package ui.tabs;

import java.awt.Component;
import java.util.List;

import javax.swing.*;
import ui.LibraryAppGUI;
import model.BookList;
import model.Book;

// Represents a tab showing a specific BookList
public class BookListTab extends JPanel {
    private LibraryAppGUI gui;
    private BookList bookList;
    private JButton backButton;
    private JButton addBookButton;

    // MODIFIES: this
    // EFFECTS: constructs a tab for a specific book list
    public BookListTab(LibraryAppGUI gui, BookList bookList) {
        this.gui = gui;
        this.bookList = bookList;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("BookList: " + bookList.getName());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(backButton);
        addBackButtonListener();

        addBookButton = new JButton("Add Book");
        addBookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(addBookButton);
        addAddBookButtonListener();

        displayBooks();
    }

    // MODIFIES: this
    // EFFECTS: displays all books in the book list
    private void displayBooks() {
        List<Book> books = bookList.getBooks();
        if (books.isEmpty()) {
            add(new JLabel("No books in this booklist"));
        } else {
            for (Book book : books) {
                add(createBookPanel(book));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: attaches action to back button that returns to library screen
    private void addBackButtonListener() {
        backButton.addActionListener(e -> {
            gui.getTabbedPane().setComponentAt(0, new LibraryTab(gui));
        });
    }

    // MODIFIES: this
    // EFFECTS: attaches action to add book button that adds a book to the book list
    private void addAddBookButtonListener() {
        addBookButton.addActionListener(e -> {
            String title = promptForRequiredField("Enter book title:");
            String author = promptForRequiredField("Enter author:");
            String genre = promptForRequiredField("Enter genre:");
            String notes = promptForRequiredField("Enter notes:");
            String contentPath = promptForRequiredField("Enter content path:");
            if (title == null || author == null || genre == null || notes == null || contentPath == null) {
                return;
            }
            double rating = promptForRating();
            Book newBook = new Book(title, author, genre, notes, contentPath);
            newBook.setRating(rating);
            bookList.addBook(newBook);
            gui.getTabbedPane().setComponentAt(0, new BookListTab(gui, bookList));
        });
    }

    // EFFECTS: prompt for a required field; returns null if blank
    private String promptForRequiredField(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return input;
    }

    // EFFECTS: prompt for a rating; returns 0 if input is invalid
    private double promptForRating() {
        String ratingInput = JOptionPane.showInputDialog(this, "Enter rating (0.0 - 5.0):");
        if (ratingInput == null || ratingInput.trim().isEmpty()) {
            return 0.0;
        }
        try {
            double rating = Double.parseDouble(ratingInput);
            if (rating < 0.0 || rating > 5.0) {
                JOptionPane.showMessageDialog(this, "Invalid rating, set to 0.");
                return 0.0;
            }
            return rating;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid rating, set to 0.");
            return 0.0;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a panel showing a book and a remove button
    private JPanel createBookPanel(Book book) {
        JPanel bookPanel = new JPanel();
        JButton bookButton = new JButton(book.getTitle() + " | Rating: " + book.getRating());
        JButton removeButton = new JButton("Remove");
        
        bookButton.addActionListener(e ->
                gui.getTabbedPane().setComponentAt(0, new BookTab(gui, bookList, book)));
        removeButton.addActionListener(e -> {
            bookList.removeBook(book.getTitle());
            gui.getTabbedPane().setComponentAt(0, new BookListTab(gui, bookList));
        });

        bookPanel.add(bookButton);
        bookPanel.add(removeButton);

        return bookPanel;
    }
}
