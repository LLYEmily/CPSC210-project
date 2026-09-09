package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookListTest {
    private BookList testBookList;
    private Book testbook1;
    private Book testbook2;
    private ArrayList<Book> bl;
    
    @BeforeEach
    void runBefore() {
        testBookList = new BookList("Favourite");
        testbook1 = new Book("testbook", "Emily", "Fantasy", "test for book class", "data/testbook.txt");
        testbook2 = new Book("testbook2", "Emily", "Non-Fiction", "test 2 for book class", "data/testbook2.txt");
        bl = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals("Favourite", testBookList.getName());
        assertEquals(0, testBookList.size());
    }

    @Test
    void testAddBook() {
        testBookList.addBook(testbook1);
        bl.add(testbook1);
        assertEquals(1, testBookList.size());
        assertEquals(bl, testBookList.getBooks());

        testBookList.addBook(testbook2);
        bl.add(testbook2);
        assertEquals(2, testBookList.size());
        assertEquals(bl, testBookList.getBooks());
    }

    @Test
    void testRemoveBook() {
        testBookList.addBook(testbook1);
        assertEquals(1, testBookList.size());
        assertTrue(testBookList.removeBook("testbook"));
        assertEquals(0, testBookList.size());

        testBookList.addBook(testbook1);
        testBookList.addBook(testbook2);
        bl.add(testbook2);
        assertEquals(2, testBookList.size());
        assertTrue(testBookList.removeBook("testbook"));
        assertEquals(1, testBookList.size());
        assertEquals(bl, testBookList.getBooks());

        assertFalse(testBookList.removeBook("a"));
        assertEquals(1, testBookList.size());
    }

    @Test
    void testFindBook() {
        assertNull(testBookList.findBook("a"));
        testBookList.addBook(testbook1);
        testBookList.addBook(testbook2);
        assertEquals(testbook1, testBookList.findBook("testbook"));
        assertEquals(testbook2, testBookList.findBook("testbook2"));
    }

    @Test
    void testAverageRating() {
        assertEquals(0.0, testBookList.getAverageRating(), 0.001);
        testBookList.addBook(testbook1);
        testbook1.setRating(2.0);
        assertEquals(2.0, testBookList.getAverageRating(), 0.001);
        testBookList.addBook(testbook2);
        testbook2.setRating(4.0);
        assertEquals(3.0, testBookList.getAverageRating(), 0.001);
    }

}


