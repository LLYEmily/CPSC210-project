package ui;

import model.EventLog;
import model.Event;

import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.LibraryTab;
import javax.swing.*;


import java.io.FileNotFoundException;
import java.io.IOException;



// Represents the GUI for Library Application
public class LibraryAppGUI extends JFrame {
    public static final int LIBRARY_TAB_INDEX = 0;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JTabbedPane sidebar;
    private Library library;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/library.json";

    // MODIFIES: this
    // EFFECTS: creates LibraryAppGUI, loads Library, displays sidebar
    public LibraryAppGUI() {
        super("Library Collection System");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        library = new Library();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                printEventLog();
                System.exit(0);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: prints all event in the EventLog to the console
    private void printEventLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
            System.out.println();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds library tab to this UI
    private void loadTabs() {
        JPanel libraryTab = new LibraryTab(this);
        sidebar.add(libraryTab, LIBRARY_TAB_INDEX);
        sidebar.setTitleAt(LIBRARY_TAB_INDEX, "Library");
        
    }
    
    //MODIFIES: this
    //EFFECTS: reloads the library tab to reflect current library state
    public void refreshLibraryTab() {
        sidebar.remove(LIBRARY_TAB_INDEX);
        sidebar.insertTab("Library", null, new LibraryTab(this), null, LIBRARY_TAB_INDEX);
        sidebar.setSelectedIndex(LIBRARY_TAB_INDEX);
    }

    // MODIFIES: this
    // EFFECTS: save the library to file
    public void saveLibrary()throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(library);
        jsonWriter.close();
    }
    
    // MODIFIES: this
    // EFFECTS: load the library from file and refresh
    public void loadLibrary()throws IOException {
        library = jsonReader.read();
        refreshLibraryTab();
    }

    //EFFECTS: returns Library object controlled by this UI
    public Library getLibrary() {
        return library;
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }
    
}
