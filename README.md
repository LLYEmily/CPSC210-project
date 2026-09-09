# Library Collection System 📚

## Project Proposal 🖊️

This project is a **Library Collection System** that allows users to organize and explore books in a structured way. Users will be able to add books with detailed information such as *title*, *genre*, *author*, rating, and personal notes. Users can use this application to browse the Library, view books, and inspect detailed information for each book.

This application is intended for students and readers who want a more thoughtful way to catalogue and organize on their personal book collection. Reading is one of my personal hobbies🤩, and this application allows me to connect that interest with software design in CPSC 210. 

**🌟 Main features of the application 🌟:**
- Creating and managing book list within the library
- Adding books to selected book list with metadata such as rating
- Viewing lists of book list and books
- Viewing detailed information about each book
- Reading the context of a book within the application

## User Stories
- As a user, I want to be able to create an new book list in my library.
- As a user, I want to be able to add a book to a selected book list and specify its title, author, genre, rating, notes, and file path to its text content.
- As a user, I want to be able to view a list of all book list in my library.
- As a user, I want to be able to view a list of all books in a selected book list.
- As a user, I want to be able to select a book and view its detailed information.
- As a user, I want to be able to remove a book from a selected book list.
- As a user, I want to be able to select a book and read its content in the application.
- As a user, I want to be able to save my entire library (all book lists and books) to file.
- As a user, I want to be able to load my library from file and resume where I left over.

## Instructions for End User

- You can view the panel that displays the Xs that have already been added to the Y by clicking on a book list in the Library screen to view all books that have been added to that book list.
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the "Add Book" button in a book list and entering the book's information and add a new book to that book list.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "Remove" button next to a book to remove it from the book list.
- You can locate my visual component by clicking on a book to open the book detail screen, where a graphical star rating (drawn using Java Graphics) and a visual book cover are displayed.
- You can save the state of my application by clicking the "Save" button on the Library screen.
- You can reload the state of my application by clicking the "Load" button on the Library screen.

## Phase 4: Task 2
Sample event log:
Sun Mar 29 10:39:57 PDT 2026
Book list created: test

Sun Mar 29 10:40:07 PDT 2026
Rating for book 'test' set to 3.0

Sun Mar 29 10:40:07 PDT 2026
Book 'test' added to book list 'test'

Sun Mar 29 10:40:13 PDT 2026
Book 'test' removed from book list 'test'

Sun Mar 29 10:40:17 PDT 2026
Book list removed: test

## Phase 4: Task 3
If I have more time to improve my project, I would refactor the GUI classes to reduce duplication between 'LibraryTab', 'BookListTab', and 'BookTab'. These classes share similar responsibilities such as setting up layouts, creating buttons, and switching between views. By extracting repeated code into helper methods, I would make the design easier to maintain and reduce repetition. This refactoring would also make future UI changes more consistent, since updates to shared behavior could be made in one place instead of several.

I would also refactor the design to improve separation of concerns between the UI and the application logic. Right now, the UI classes directly perform actions on the model. If I had more time, I might introduce a controller layer to handle these operations, like creating book lists, adding books, and removing books. This would make the code more easier to extend, and making the UI classes simpler. 
