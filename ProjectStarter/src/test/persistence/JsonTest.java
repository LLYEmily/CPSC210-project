package persistence;

import model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkBook(String title, String author, String genre, 
                             double rating, String notes, String contentPath, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
        assertEquals(rating, book.getRating());
        assertEquals(notes, book.getNotes());
        assertEquals(contentPath, book.getContentPath());
    }
}