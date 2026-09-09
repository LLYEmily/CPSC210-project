package persistence;

import model.Book;
import model.BookList;
import model.Library;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderEmptyLibrary() throws IOException {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibrary.json");
        Library lib = reader.read();
        assertNotNull(lib);
        assertEquals(0, lib.size());
    }

    @Test
    void testReaderGeneralLibrary() throws IOException {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLibrary.json");
        Library lib = reader.read();
        assertEquals(2, lib.size());
        BookList favorites = lib.getBookList("Favorites");
        assertEquals(1, favorites.size());
        Book b1 = favorites.findBook("test");
        assertNotNull(favorites);
        checkBook("test", "Emily", "testbook", 4.5, "hiii", "./data/testbook.txt", b1);
        BookList toRead = lib.getBookList("To read");
        assertEquals(1, toRead.size());
        Book b2 = toRead.findBook("test2");
        assertNotNull(toRead);
        checkBook("test2", "Emily", "testbook2", 0.5, "hiiiii", "./data/testbook2.txt", b2);
    }

    @Test
    void testReaderMixedLibrary() throws IOException {
        JsonReader reader = new JsonReader("./data/testReaderMixedLibrary.json");
        Library lib = reader.read();
        assertEquals(2, lib.size());
        BookList empty = lib.getBookList("Empty");
        assertNotNull(empty);
        assertEquals(0, empty.size());
        BookList favorites = lib.getBookList("Favorites");
        assertEquals(1, favorites.size());
        assertNotNull(favorites);
    }

    @Test
    void testReaderComplexLibrary() throws IOException {
        JsonReader reader = new JsonReader("./data/testReaderComplexLibrary.json");
        Library lib = reader.read();
        assertEquals(2, lib.size());
        BookList empty = lib.getBookList("Empty");
        assertNotNull(empty);
        assertEquals(0, empty.size());

        BookList favorites = lib.getBookList("Favorites");
        assertEquals(2, favorites.size());
        assertNotNull(favorites);
        Book b2 = favorites.findBook("test2");
        checkBook("test2", "Emily", "testbook2", 0.5, "hiiiii", "./data/testbook2.txt", b2);
    }
}
    
