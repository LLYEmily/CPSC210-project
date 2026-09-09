package ui.tabs;

import javax.swing.*;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.BookList;
import ui.LibraryAppGUI;

// Represents the main library tab in GUI
public class LibraryTab extends JPanel {
    private LibraryAppGUI gui;
    private JButton addBookListButton;
    private JButton saveButton;
    private JButton loadButton;

    // MODIFIES: this
    // EFFECTS: constructs library tab
    public LibraryTab(LibraryAppGUI gui) {
        this.gui = gui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addTitle();
        if (gui.getLibrary().getBookLists().isEmpty()) {
            add(new JLabel("No booklists yet"));
        } else {
            for (BookList bl : gui.getLibrary().getBookLists()) {
                add(createBookListPanel(bl));
            }
        }

        addAddBookListButton();

        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);
        add(loadButton);
        addSaveButtonListener();
        addLoadButtonListener();
    }

    // MODIFES
    // EFFECTS: add title label
    private void addTitle() {
        JLabel title = new JLabel("My Library📚");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel showing a booklist and a remove button
    private JPanel createBookListPanel(BookList bl) {
        JPanel panel = new JPanel();
        JButton openButton = new JButton(bl.getName());
        JButton removeButton = new JButton("Remove");
        
        openButton.addActionListener(e ->
                gui.getTabbedPane().setComponentAt(0, new BookListTab(gui, bl))
        );

        removeButton.addActionListener(e -> {
            gui.getLibrary().removeBookList(bl.getName());
            gui.getTabbedPane().setComponentAt(0, new LibraryTab(gui));
        });

        panel.add(openButton);
        panel.add(removeButton);

        return panel;
    }

    // MODIFES
    // EFFECTS: add title label
    private void addAddBookListButton() {
        addBookListButton = new JButton("Add BookList");
        addBookListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(addBookListButton);
        addBookListButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter book list name:");
            if (name == null || name.trim().isEmpty()) {
                return;
            }
            if (gui.getLibrary().getBookList(name) != null) {
                JOptionPane.showMessageDialog(this, "Book list already exists.");
                return;
            }
            gui.getLibrary().createBookList(name);
            gui.getTabbedPane().setComponentAt(0, new LibraryTab(gui));
        });
    }

    // MODIFES
    // EFFECTS: attaches save action to save button
    private void addSaveButtonListener() {
        saveButton.addActionListener(e -> {
            try {
                gui.saveLibrary();
                JOptionPane.showMessageDialog(this, "Library saved successfully.");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Unable to save library.");
            }
        });
    }

    // MODIFES
    // EFFECTS: attaches load action to load button
    private void addLoadButtonListener() {
        loadButton.addActionListener(e -> {
            try {
                gui.loadLibrary();
                JOptionPane.showMessageDialog(this, "Library loaded successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Unable to load library.");
            }
        });
    }
    
}
