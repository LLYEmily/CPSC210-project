package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    private Library testLibrary;
    private BookList testBookList1;
    private BookList testBookList2;

    @BeforeEach
    void runBefore() {
        testLibrary = new Library();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testLibrary.size());
    }

    @Test
    void testCreateBook() {
        assertEquals(0, testLibrary.size());
        testLibrary.createBookList("testBookList1");
        assertEquals(1, testLibrary.size());
    }

    @Test
    void testRemoveBookList() {
        assertFalse(testLibrary.removeBookList("testBookList1"));
        testLibrary.createBookList("testBookList1");
        assertTrue(testLibrary.removeBookList("testBookList1"));
        assertEquals(0, testLibrary.size());
        assertFalse(testLibrary.removeBookList("testBookList2"));
        assertNull(testLibrary.getBookList("testBookList1"));
        testLibrary.createBookList("testBookList1");
        testLibrary.createBookList("testBookList2");
        assertFalse(testLibrary.removeBookList("testBookList3"));
        assertEquals(2, testLibrary.size());
    }

    @Test
    void testGetBookList() {
        assertNull(testLibrary.getBookList("a"));
        assertEquals(0, testLibrary.getBookLists().size());
        testLibrary.createBookList("testBookList1");
        assertEquals(1, testLibrary.getBookLists().size());
        testLibrary.createBookList("testBookList2");
        testBookList1 = testLibrary.getBookList("testBookList1");
        testBookList2 = testLibrary.getBookList("testBookList2");
        assertNull(testLibrary.getBookList("a"));
        assertEquals("testBookList1", testBookList1.getName());
        assertEquals("testBookList2", testBookList2.getName());
        assertEquals(testBookList1, testLibrary.getBookLists().get(0));
        assertEquals(testBookList2, testLibrary.getBookLists().get(1));
    }

}



