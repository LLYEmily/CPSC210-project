package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a list of Books in the library
public class BookList implements Writable {
    private String name;
    private ArrayList<Book> books;

    // Requires: name is not empty
    // MODIFIES: this
    // EFFECTS: construct an empty book list with given name
    public BookList(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    // EFFECTS: returns the name of the book list
    public String getName() {
        return name;
    }

    // MODIFES: this
    // EFFECTS: add book to this book list
    public void addBook(Book book) {
        books.add(book);
        EventLog.getInstance().logEvent((
            new Event("Book '" + book.getTitle() + "' added to book list '" + name + "'")));
    }

    // MODIFIES: this
    // EFFECTS: remove the first book with given title from this book list, 
    // returns true if book was removed, false otherwise
    public boolean removeBook(String title) {
        for (Book b : books) {
            if (b.getTitle().equals(title)) {
                books.remove(b);
                EventLog.getInstance().logEvent((
                    new Event("Book '" + title + "' removed from book list '" + name + "'")));
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the first book with given title in this book list
    public Book findBook(String title) {
        for (Book b : books) {
            if (b.getTitle().equals(title)) {
                return b;
            }
        }
        return null;
    }

    // EEFECTS: returns a list of books in this book list
    public ArrayList<Book> getBooks() {
        return books;
    }

    // EFFFECTS: returns the number of books in this book list
    public int size() {
        return books.size();
    }

    // EFFFECTS: returns the average rating of all books in the book list or 0.0 if no book
    public double getAverageRating() {
        if (books.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Book b : books) {
            total += b.getRating();
        }
        return total / books.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        return json;
    }

    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b: books) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}
