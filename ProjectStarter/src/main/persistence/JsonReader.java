package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.*;

import model.Book;
import model.BookList;
import model.Library;

// Represents a reader that reads Library from Json data stored in file
// Inspired from Sample JsonSerializationDemo in CPSC 210
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return Files.readString(Paths.get(source));
    }

    // EFFECTS: parses Library from JSON object and returns it
    private Library parseLibrary(JSONObject jsonObject) {
        Library lib = new Library();
        addBookLists(lib, jsonObject);
        return lib;
    }

    // MODIFIES: lib
    // EFFECTS: parses book lists from Json object and add them to library
    private void addBookLists(Library lib, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("bookLists");
        for (Object obj : jsonArray) {
            JSONObject nextBookList = (JSONObject) obj;
            addBookList(lib, nextBookList);
        }
    }

    // MODIFIES: lib
    // EFFECTS: parses a book list from Json object and add them to library
    private void addBookList(Library lib, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        lib.createBookList(name);
        BookList bl = lib.getBookList(name);
        JSONArray books = jsonObject.getJSONArray("books");
        for (Object obj : books) {
            JSONObject nextBook = (JSONObject) obj;
            bl.addBook(parseBook(nextBook));
        }
    }

    // EFFECTS: parses a book from JSON object and returns it
    private Book parseBook(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        String genre = jsonObject.getString("genre");
        double rating = jsonObject.getDouble("rating");
        String notes = jsonObject.getString("notes");
        String contentPath = jsonObject.getString("contentPath");
        Book b = new Book(title, author, genre, notes, contentPath);
        b.setRating(rating);
        return b;
    }
}
