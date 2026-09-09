package persistence;

import model.Book;
import model.BookList;
import model.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
        assertThrows(IOException.class, writer::open);
    }

    @Test
    void testWriterEmptyLibrary() throws IOException {
        Library lib = new Library();
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibrary.json");
        writer.open();
        writer.write(lib);
        writer.close();

        JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
        lib = reader.read();
        assertEquals(0, lib.size());
    }

    @Test
    void testWriterWithEmptyBookList() throws IOException {
        Library lib = new Library();
        lib.createBookList("x");
        JsonWriter writer = new JsonWriter("./data/testWriterLibraryWithEmptyList.json");
        writer.open();
        writer.write(lib);
        writer.close();

        JsonReader reader = new JsonReader("./data/testWriterLibraryWithEmptyList.json");
        Library readBack = reader.read();
        assertEquals(1, readBack.size());
        BookList x = readBack.getBookList("x");
        assertNotNull(x);
        assertEquals(0, x.size());
    }


    @Test
    void testWriterGeneralLibrary() throws IOException {
        Library lib = makeGeneralLibrary();
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
        writer.open();
        writer.write(lib);
        writer.close();
        JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
        Library readBack = reader.read();
        assertNotNull(readBack);
        assertEquals(2, readBack.size());
        BookList favBack = readBack.getBookList("Favorites");
        assertEquals(1, favBack.size());
        checkBook("test", "Emily", "testbook", 4.5, "hiii", "./data/testbook.txt", favBack.findBook("test"));
        BookList toReadBack = readBack.getBookList("To read");
        assertEquals(1, toReadBack.size());
        checkBook("test2", "Emily", "testbook2", 0.5, "hiiiii", "./data/testbook2.txt", 
                    toReadBack.findBook("test2"));
    }

    private Library makeGeneralLibrary() {
        Library lib = new Library();
        lib.createBookList("Favorites");
        BookList favorites = lib.getBookList("Favorites");
        Book b1 = new Book("test", "Emily", "testbook", "hiii", "./data/testbook.txt");
        b1.setRating(4.5);
        favorites.addBook(b1);
        lib.createBookList("To read");
        BookList toRead = lib.getBookList("To read");
        Book b2 = new Book("test2", "Emily", "testbook2", "hiiiii", "./data/testbook2.txt");
        b2.setRating(0.5);
        toRead.addBook(b2);
        return lib;
    }

}