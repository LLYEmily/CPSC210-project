package ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import model.Book;
import model.BookList;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

// Console application for managing a Library Collection System
// The console part is inspired from Lab4.2 FlashcardReviewer application in CPSC 210
public class LibraryApp {
    private Library library;
    private final Scanner scanner;
    private boolean isProgramRunning;
    private BookList currentBookList;
    private Book currentBook;
    private static final String JSON_STORE = "./data/library.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: constructs LibraryApp
    public LibraryApp() {
        this.library = new Library();
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
        this.currentBookList = null;
        this.currentBook = null;
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        
        printDivider();
        System.out.println("Welcome to the Library Collection System :)");
        printDivider();
        
        while (isProgramRunning) {
            handleLibraryMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: display and proccess user commands for library menu
    private void handleLibraryMenu() {
        printLibraryOverview();
        printLibraryMenu();
        String input = scanner.nextLine().trim().toLowerCase();
        processLibraryCommands(input);
    }

    // EFFECTS: print all the book list in library
    private void printLibraryOverview() {
        System.out.println("===Library===");
        if (library.size() == 0) {
            System.out.println("No book lists yet");
        } else {
            System.out.println("Book lists:");
            for (BookList bl : library.getBookLists()) {
                System.out.println("- " + bl.getName() + " (" + bl.size() + " books)");

            }
        }
        printDivider();
    }

    // EFFECTS: print all the commands that user can use in library
    private void printLibraryMenu() {
        System.out.println("Library options:");
        System.out.println("create : create a new book list");
        System.out.println("remove : remove a book list");
        System.out.println("open   : open a book list");
        System.out.println("save   : save library to file");
        System.out.println("load   : load library from file");
        System.out.println("quit   : quit");
        printDivider();
        System.out.print("Enter option: ");
    }

    // MODIFIES: this
    // EFFECTS: process user's input in library menu
    private void processLibraryCommands(String input) {
        printDivider();
        switch (input) {
            case "create":
                createBookList();
                break;
            case "remove":
                removeBookList();
                break;
            case "open":
                openBookList();
                break;
            case "save":
                saveLibrary();
                break;
            case "load":
                loadLibrary();
                break;
            case "quit":
                quitApplication();
                break;
            default:
                System.out.println("Invalid option, plase try again:)");
        } printDivider();
    }

    // MODIFIES: this
    // EFFECTS: create new book list
    private void createBookList() {
        System.out.print("Enter new book list name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty");
            return;
        }
        
        if (library.getBookList(name) != null) {
            System.out.println("Error: A book list with this name already exist");
            return;
        }

        library.createBookList(name);
        System.out.println("Created book list: " + name);
    }

    // MODIFIES: this
    // EFFECTS: remove book list
    private void removeBookList() {
        if (library.size() == 0) {
            System.out.println("No book lists in library");
            return;
        }

        System.out.print("Enter book list name to remove: ");
        String name = scanner.nextLine().trim();
        if (library.removeBookList(name)) {
            System.out.println("Removed book list: " + name);
        } else {
            System.out.println("Book list not found: " + name);
        }
    }

    // MODIFIES: this
    // EFFECTS: open a book list
    private void openBookList() {
        if (library.size() == 0) {
            System.out.println("No book lists in library yet :(");
            return;
        }

        System.out.print("Enter book list name to open: ");
        String name = scanner.nextLine().trim();
        BookList bl = library.getBookList(name);
        if (bl == null) {
            System.out.println("Book list not found: " + name);
            return;
        }

        currentBookList = bl;
        runBookListMenu(bl);
        currentBookList = null;
    }

    // MODIFIES: this
    // EFFECTS: save the library to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("Saved library to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: load the library from file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
            currentBookList = null;
            currentBook = null;
            System.out.println("Loaded library from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: runs a book list menu
    private void runBookListMenu(BookList bl) {
        boolean keepGoing = true;
        while (keepGoing) {
            handleBookListMenu();
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("back")) {
                keepGoing = false;
                System.out.println("Returning to library");
            } else {
                processBookListCommands(input);
            }
        }
    }

    // EFFECTS: display and proccess user commands for book list menu
    private void handleBookListMenu() {
        printBookListOverview();
        printBookListMenu();
    }

    // EFFECTS: print all the books and the average rating of the book
    private void printBookListOverview() {
        System.out.println("=== Book List: " + currentBookList.getName() + " ===");
        if (currentBookList.size() == 0) {
            System.out.println("No books yet");
        } else {
            System.out.println("Books:");
            for (Book b : currentBookList.getBooks()) {
                System.out.println("- " + b.getTitle() + " (rating: " + b.getRating() + ")");

            }
        }
        System.out.println("Average rating: " + currentBookList.getAverageRating());
        printDivider();
    }


    // EFFECTS: print all the commands that user can use in book list
    private void printBookListMenu() {
        System.out.println("Book list options:");
        System.out.println("add    : add a new book");
        System.out.println("remove : remove a book");
        System.out.println("open   : open a book ");
        System.out.println("back   : go back");
        printDivider();
        System.out.print("Enter option: ");
    }

    // MODIFIES: this
    // EFFECTS: process user's input in book list menu
    private void processBookListCommands(String input) {
        printDivider();
        switch (input) {
            case "add":
                addBook();
                break;
            case "remove":
                removeBook();
                break;
            case "open":
                openBook();
                break;
            default:
                System.out.println("Invalid option, plase try again:)");
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: Add new book to cuurent book list
    private void addBook() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        if (currentBookList.findBook(title) != null) {
            System.out.println("Error: A book with this title already exist in this book list");
            return;
        }

        System.out.print("Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();

        System.out.print("Notes: ");
        String notes = scanner.nextLine().trim();

        System.out.print("Content file path: ");
        String path = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || path.isEmpty()) {
            System.out.println("Title/author/genre/path cannot be empty");
            return;
        }

        Book b = new Book(title, author, genre, notes, path);
        currentBookList.addBook(b);
        System.out.println("Added book: " + title);
    }

    // MODIFIES: this
    // EFFECTS: remove book
    private void removeBook() {
        if (currentBookList.size() == 0) {
            System.out.println("No books in book list");
            return;
        }

        System.out.print("Enter book title to remove: ");
        String name = scanner.nextLine().trim();
        if (currentBookList.removeBook(name)) {
            System.out.println("Removed book: " + name);
        } else {
            System.out.println("Book not found: " + name);
        }
    }

    // MODIFIES: this
    // EFFECTS: open a book
    private void openBook() {
        if (currentBookList.size() == 0) {
            System.out.println("No books in book list");
            return;
        }

        System.out.print("Enter book title to open: ");
        String name = scanner.nextLine().trim();

        Book b = currentBookList.findBook(name);
        if (b == null) {
            System.out.println("Book not found: " + name);
            return;
        }

        currentBook = b;
        runBookMenu();
        currentBook = null;
    }

    // MODIFIES: this
    // EFFECTS: run the book menu
    private void runBookMenu() {
        boolean keepGoing = true;
        while (keepGoing) {
            handleBookMenu();
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("back")) {
                keepGoing = false;
                System.out.println("Returning to book list");
            } else {
                processBookCommands(input);
            }
        }
    }

    // EFFECTS: display and proccess user commands for book menu
    private void handleBookMenu() {
        printBookOverview();
        printBookMenu();
    }

    // EFFECTS: print details of the book
    private void printBookOverview() {
        System.out.println("=== Book Details ===");
        System.out.println("Title: " + currentBook.getTitle());
        System.out.println("Author: " + currentBook.getAuthor());
        System.out.println("Genre: " + currentBook.getGenre());
        System.out.println("Rating: " + currentBook.getRating());
        System.out.println("Notes: " + currentBook.getNotes());
        System.out.println("Content path: " + currentBook.getContentPath());
        printDivider();
    }


    // EFFECTS: print all the commands that user can use in book
    private void printBookMenu() {
        System.out.println("Book options:");
        System.out.println("rate  : set rating");
        System.out.println("read  : read content");
        System.out.println("back  : go back");
        printDivider();
        System.out.print("Enter option: ");
    }

    // MODIFIES: this
    // EFFECTS: process user's input in book menu
    private void processBookCommands(String input) {
        printDivider();
        switch (input) {
            case "rate":
                setRating();
                break;
            case "read":
                printBookContent();
                break;
            default:
                System.out.println("Invalid option, plase try again:)");
        }
        printDivider();
    }

    // EFFECTS: print the textfile of the current book
    private void printBookContent() {
        try {
            String content = Files.readString(Paths.get(currentBook.getContentPath()));
            System.out.println("--Book Content--");
            System.out.println(content);
            System.out.println("--End--");
        } catch (IOException e) {
            System.out.println("Can't read file" + currentBook.getContentPath());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets rating for current book
    private void setRating() {
        System.out.print("Enter rating (0.0 - 5.0): ");
        try {
            double rating = Double.parseDouble(scanner.nextLine().trim());
            if (rating < 0.0 || rating > 5.0) {
                System.out.println("Rating must be between 0.0 and 5.0");
                return;
            }
            currentBook.setRating(rating);
            System.out.println("Rating updated!:)");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        }
    }

    // MODIFIES: this
    // EFFECTS: print a closing message and program is not running
    private void quitApplication() {
        System.out.println("Thanks for using the Library System");
        isProgramRunning = false;
    }

    // EFFECTS: print a divider
    private void printDivider() {
        System.out.println("--------------------------------------");
    }
}
