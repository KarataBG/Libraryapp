# Libraryapp
 Унибит проект по мобилни приложения

Library Management App

The Library Management App is an Android application designed to facilitate the borrowing and returning of books in a library. The app provides a user-friendly interface for managing book transactions and keeping track of the borrowing history.
Features

The Library Management App offers the following features:

    User Authentication: Users can log in to the app using their username and password. The app verifies the credentials against a user database stored in the res/raw/users.txt file.

    Start Menu: After logging in, users are directed to the Start Menu. This menu displays the current user's name and provides options to perform various actions.

    Borrow Book: In the Borrow Book section, users can browse the available books. The app reads the book data from the res/raw/books.txt file, which includes information such as the book ID, name, author, user ID, availability status, and date of borrowing. Users can borrow a book by entering its ID. The app checks if the book is available (isTaken = 0) and verifies that the entered ID matches an available book. If the conditions are met, the app updates the book record by assigning the user's ID, marking it as taken (isTaken = 1), and recording the date of borrowing. The updated book data is then saved to the books.txt file.

    Return Book: The Return Book section allows users to view the books they have borrowed. The app retrieves the user's borrowing history by searching for book records with the user's ID. Users can return a book by entering its ID. The app verifies that the entered ID matches a book the user has borrowed. If the book is found, the app updates the book record by marking it as available (isTaken = 0), removing the user's ID, and clearing the date of borrowing. The updated book data is then saved to the books.txt file.

    History of Books: The History of Books section displays the borrowing history. The app reads the transaction records from the res/raw/history.txt file, which includes the transaction ID, user ID, book ID, and the date of borrowing. This section provides a chronological view of all past book transactions.

File Structure

The Library Management App utilizes the following files:

    res/raw/users.txt: This file stores the username and password fields for user authentication. Each line in the file represents a user and follows the format: username,password.

    res/raw/books.txt: The books.txt file stores the book records. Each line represents a book and follows the format: id,name,author,userID,isTaken,dateTaken. The isTaken field indicates the availability status of the book (0 for available, 1 for taken). If the book is taken, the userID field stores the ID of the user who borrowed it, and the dateTaken field records the date of borrowing.

    data/user/0/com.example.libraryapp/files/userX.txt: This file maintains the borrowing history. Each line represents a transaction and follows the format: transactionID,userID,bookID,date. The transactionID uniquely identifies each transaction, the userID represents the user who borrowed the book, the bookID represents the borrowed book, and the date indicates the date of borrowing.

How to Run the App

To run the Library Management App, follow these steps:

    Clone the repository or download the source code.
    Open the project in Android Studio.
    Build and run the app on an emulator or physical Android device.
    Use the app's login screen to enter your username and password (obtained from the users.txt file).
    Once logged in, you will be directed to the Start Menu, where you can choose to borrow a book, return a book, or view the borrowing history.
    In the Borrow Book section, enter the book ID to borrow a book.
    In the Return Book section, enter the book ID to return a book.
    In the History of Books section, you can view the past borrowing transactions.

Technologies Used

The Library Management App is developed using the following technologies:

    Android Studio: The Integrated Development Environment (IDE) used for Android app development.
    Java: The programming language used for developing the Android app.

Future Enhancements

While the current version of the Library Management App provides basic functionality for managing book borrowing, there are several potential areas for future enhancements:

    User Registration: Implement a user registration feature to allow new users to create an account within the app.
    Search and Filtering: Improve the book browsing functionality with enhanced search and filtering options, enabling users to find books based on criteria such as author, genre, or publication date.
    User Profile Management: Provide functionality for users to manage their profiles, including updating personal information, changing passwords, and viewing borrowing history specific to each user.
    Integration with External Systems: Integrate the app with external library systems or databases to enable centralized book management, real-time availability updates, and seamless integration with other library management processes.
