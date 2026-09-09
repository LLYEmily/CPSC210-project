package model;

import persistence.Writable;
import org.json.JSONObject;

// Represent a book with a title, author, genre, rating, notes, and content path
public class Book implements Writable {
    private String title;
    private String author;
    private String genre;
    private double rating;
    private String notes;
    private String contentPath;

    // REQUIRES: title, author, genre. contentPath are not empty
    // MODIFIES: this
    // EFFECTS: construct a Book object with given info and rating 0.0
    public Book(String title, String author, String genre, String notes, String contentPath) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.notes = notes;
        this.contentPath = contentPath;
        this.rating = 0.0;
    }

    // REQUIRES: 0.0 <= rating <= 5.0
    // MODIFIES: this
    // EFFECTS: set the rating of the book
    public void setRating(double rating) {
        this.rating = rating;
        EventLog.getInstance().logEvent((
                    new Event("Rating for book '" + title + "' set to " + rating)));
    }

    // EFFECTS: returns the rating of the book
    public double getRating() {
        return rating; 
    }

    // EFFECTS: returns the title of the book
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns the author of the book
    public String getAuthor() {
        return author;
    }

    // EFFECTS: returns the genre of the book
    public String getGenre() {
        return genre;
    }

    // EFFECTS: returns the notes of the book
    public String getNotes() {
        return notes; 
    }

    // EFFECTS: returns the file path to the book's content
    public String getContentPath() {
        return contentPath; 
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("genre", genre);
        json.put("rating", rating);
        json.put("notes", notes);
        json.put("contentPath", contentPath);
        return json;
    }
}
