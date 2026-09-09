package ui.tabs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

import ui.LibraryAppGUI;
import model.BookList;
import model.Book;

// Represents a tab showing details of a specific book
// Inspired by Java Swing Graphics examples for drawing shapes
public class BookTab extends JPanel {
    private static final int COVER_WIDTH = 140;
    private static final int COVER_HEIGHT = 180;
    private static final int STAR_PANEL_WIDTH = 240;
    private static final int STAR_PANEL_HEIGHT = 60;
    private LibraryAppGUI gui;
    private BookList bookList;
    private JButton backButton;
    private Book book;

    // MODIFIES: this
    // EFFECTS: constructs a tab for a specific book
    public BookTab(LibraryAppGUI gui, BookList bookList, Book book) {
        this.gui = gui;
        this.bookList = bookList;
        this.book = book;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Title: " + book.getTitle()));
        add(new JLabel("Author: " + book.getAuthor()));
        add(new JLabel("Genre: " + book.getGenre()));
        add(new JLabel("Notes: " + book.getNotes()));
        add(new JLabel("Content Path: " + book.getContentPath()));
        add(new JLabel("Rating: " + book.getRating()));

        add(new JLabel("Book Cover: "));
        add(new BookCoverPanel(book.getTitle()));
        add(new JLabel("Rating: "));
        add(new StarPanel(book.getRating()));

        backButton = new JButton("Back");
        add(backButton);
        addBackButtonListener();

    }

    // MODIFIES: this
    // EFFECTS: attaches action to back button that returns to booklist screen
    private void addBackButtonListener() {
        backButton.addActionListener(e -> {
            gui.getTabbedPane().setComponentAt(0, new BookListTab(gui, bookList));
        });
    }

    // Represents a panel that draws a simple book cover
    private static class BookCoverPanel extends JPanel {
        private String title;

        // MODIFIES: this 
        // EFFECTS: constructs a panel showing a visual book cover
        public BookCoverPanel(String title) {
            this.title = title;
            setPreferredSize(new Dimension(COVER_WIDTH, COVER_HEIGHT));
        }

        // MODIFIES: this
        // EFFECTS: paints a simple book cover with title
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(200, 220, 255));
            g2.fillRect(20, 10,100, 150);

            g2.setColor(Color.BLACK);
            g2.drawRect(20, 10,100, 150);

            g2.setColor(new Color(189, 224, 254));
            g2.fillRect(20, 10,100, 150);

            g2.setColor(Color.BLACK);
            g2.drawString("BOOK", 50, 40);
            if (title.length() <= 10) {
                g2.drawString(title, 40, 80);
            } else {
                g2.drawString(title.substring(0, 10) + "...", 40, 80);
            }
        }
    }

    // Represents a panel that draws a graphical star rating
    private static class StarPanel extends JPanel {
        private double rating;

        // MODIFIES: this 
        // EFFECTS: constructs a panel showing a graphical star rating
        public StarPanel(double rating) {
            this.rating = rating;
            setPreferredSize(new Dimension(STAR_PANEL_WIDTH, STAR_PANEL_HEIGHT));
        }

        // MODIFIES: this
        // EFFECTS: paints start
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int stars = (int) Math.round(rating);
            for (int i = 0; i < stars; i++) {
                Shape star = createStarShape(20 + i * 40, 10, 30, 30);
                g2.setColor(new Color(255, 215, 0));
                g2.fill(star);
                
                g2.setColor(Color.BLACK);
                g2.draw(star);
            }
        }

        // EFFECTS: returns a five-point star shape
        private Shape createStarShape(int x, int y, int width, int height) {
            Path2D.Double star = new Path2D.Double();
            star.moveTo(x + width / 2.0, y);
            star.lineTo(x + width * 0.65, y + height * 0.35);
            star.lineTo(x + width, y + height * 0.4);
            star.lineTo(x + width * 0.75, y + height * 0.65);
            star.lineTo(x + width * 0.82, y + height);
            star.lineTo(x + width / 2.0, y + height * 0.8);
            star.lineTo(x + width * 0.18, y + height);
            star.lineTo(x + width * 0.25, y + height * 0.65);
            star.lineTo(x, y + height * 0.4);
            star.lineTo(x + width * 0.35, y + height * 0.35);
            star.closePath();
            return star;
        }
    }
    
}
