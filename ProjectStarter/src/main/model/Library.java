package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represent a library that contains different book list
public class Library implements Writable {
    private ArrayList<BookList> bookLists;

    // MODIFIES: this
    // EFFECTS: construct an empty library
    public Library() {
        bookLists = new ArrayList<>();
    }

    // REQUIRES: name is non-empty
    // MODIFIES: this
    // EFFECTS: create a new book list with the given name
    public void createBookList(String name) {
        bookLists.add(new BookList(name));
        EventLog.getInstance().logEvent((new Event("Book list created: " + name)));
    }

    // MODIFIES: this
    // EFFECTS: remove the book list with the fiven name from library, return true if removed, false otherwise
    public boolean removeBookList(String name) {
        for (BookList bl : bookLists) {
            if (bl.getName().equals(name)) {
                bookLists.remove(bl);
                EventLog.getInstance().logEvent((new Event("Book list removed: " + name)));
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return the list of all book list
    public ArrayList<BookList> getBookLists() {
        return bookLists;
    }

    // EFFECTS: return the book list with the given name or null if not found
    public BookList getBookList(String name) {
        for (BookList bl : bookLists) {
            if (bl.getName().equals(name)) {
                return bl;
            }
        }
        return null;
    }

    // EFFECTS: return the number of book lists
    public int size() {
        return bookLists.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("bookLists", bookListsToJson());
        return json;
    }

    private JSONArray bookListsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BookList bl: bookLists) {
            jsonArray.put(bl.toJson());
        }
        return jsonArray;
    }
}
