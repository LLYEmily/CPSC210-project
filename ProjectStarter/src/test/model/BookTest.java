package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {
    private Book testbook;
    
    @BeforeEach
    void runBefore() {
        testbook = new Book("testbook", "Emily", "Fantasy", "test for book class", "data/testbook.txt");
    }

    @Test
    void testConstructor() {
        assertEquals("testbook", testbook.getTitle());
        assertEquals("Emily", testbook.getAuthor());
        assertEquals("Fantasy", testbook.getGenre());
        assertEquals("test for book class", testbook.getNotes());
        assertEquals("data/testbook.txt", testbook.getContentPath());
        assertEquals(0.0, testbook.getRating(), 0.001);
    }

    @Test
    void testSetRating() {
        testbook.setRating(2.5);
        assertEquals(2.5, testbook.getRating(), 0.001);
    }
}

