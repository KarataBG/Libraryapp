# Libraryapp
 Унибит проект по мобилни приложения
 
 Приложение за управление на библиотека

Приложението за управление на библиотеката е приложение за Android, предназначено да улесни заемането и връщането на книги в библиотека. Приложението предоставя удобен за потребителя интерфейс за управление на транзакции с книги и проследяване на историята на заемането. Характеристика

Приложението за управление на библиотека предлага следните функции:

     Удостоверяване на потребителя: Потребителите могат да влизат в приложението, като използват своето потребителско име и парола. Приложението проверява идентификационните данни спрямо потребителска база данни, съхранена във файла res/raw/users.txt.

     Меню "Старт": След влизане потребителите се насочват към менюто "Старт". Това меню показва името на текущия потребител и предоставя опции за извършване на различни действия.

     Взимане на книга: В раздела "Borrow Book" потребителите могат да разглеждат наличните книги. Приложението чете данните за книгата от файла "res/raw/books.txt", който включва информация като идентификатор на книгата, име, автор, потребителско име, състояние на наличност и дата на заемане. Потребителите могат да заемат книга, като въведат нейния идентификатор. Приложението проверява дали книгата е налична (isTaken = 0) и проверява дали въведеният идентификатор съответства на налична книга. Ако условията са изпълнени, приложението актуализира записа на книгата, като присвоява ID на потребителя, маркира го като зает (isTaken = 1) и записва датата на заемане. След това актуализираните данни за книгата се записват във файла "res/raw/books.txt".

     Книга за връщане: Секцията за "Return Book" позволява на потребителите да преглеждат книгите, които са взели назаем. Приложението извлича хронологията на заемането на потребителя, като търси записи в книги с идентификатора на потребителя. Потребителите могат да върнат книга, като въведат нейния ID. Приложението проверява дали въведеният идентификатор съответства на книга, която потребителят е заел. Ако книгата бъде намерена, приложението актуализира записа на книгата, като я маркира като налична (isTaken = 0), премахва ИД на потребителя и изчиства датата на заемане. След това актуализираните данни за книгата се записват във файла "res/raw/books.txt".

     История на книги: Разделът "History Books" показва историята на заемането. Приложението чете записите на транзакциите от файла data/user/0/com.example.libraryapp/files/userX.txt (X = ИД текущ потребител), който включва потребителския идентификатор, идентификатора на книгата и датата на заемане. Този изглед представя хронологично всички минали транзакции с книги на потребителя.

Файлова структура

Приложението за управление на библиотеката използва следните файлове:

    res/raw/users.txt: Този файл съхранява полетата за потребителско име и парола за удостоверяване на потребителя. Всеки ред във файла представлява потребител и следва формата: потребителско име, парола.

    res/raw/books.txt: Действа като резервно копие на книгите ако трябва да се възстановят.

    data/user/0/com.example.libraryapp/files/books.txt: Файлът books.txt съхранява записите на книгите. Всеки ред представлява книга и следва формата: id,name,author,userID,isTaken,dateTaken. Полето isTaken показва състоянието на наличност на книгата (0 за налична, 1 за заета). Ако книгата бъде взета, полето userID съхранява идентификатора на потребителя, който я е заел, а полето dateTaken записва датата на заемане.

    data/user/0/com.example.libraryapp/files/userX.txt: Този файл поддържа хронологията на заемането. Всеки ред представлява транзакция и следва формата: транзакцияID,userID,bookID,date. TransactionID уникално идентифицира всяка транзакция, userID представлява потребителя, който е заел книгата, bookID представлява заетата книга, а датата показва датата на заемане.
    Как да стартирате приложението

За да стартирате приложението за управление на библиотека, изпълнете следните стъпки:

    Клонирайте проекта или изтеглете изходния код.
    Отворете проекта в Android Studio.
    Създайте и стартирайте приложението на емулатор или физическо устройство с Android.
    Използвайте екрана за влизане в приложението, за да въведете вашето потребителско име и парола (получени от файла users.txt).
    След като влезете, ще бъдете насочени към менюто "Старт", където можете да изберете да заемете книга, да върнете книга или да видите хронологията на заемането.
    В раздела Заемане на книга въведете идентификатора на книгата, за да заемете книга.
    В раздела за връщане на книга въведете идентификатора на книгата, за да върнете книга.
    В секцията История на книгите можете да видите минали транзакции за заемане.
    Използвани технологии

Приложението за управление на библиотека е разработено с помощта на следните технологии:

    Android Studio: Интегрирана среда за разработка (IDE), използвана за разработка на приложения за Android.
    Java: Езикът за програмиране, използван за разработване на приложението за Android.
Бъдещи подобрения

Въпреки че настоящата версия на приложението за управление на библиотека предоставя основна функционалност за управление на заемането на книги, има няколко потенциални области за бъдещи подобрения:

    Регистрация на потребител: Внедрете функция за регистрация на потребител, за да позволите на нови потребители да създадат акаунт в приложението.
    Търсене и филтриране: Подобрена функционалност за разглеждане на книги с подобрени опции за търсене и филтриране, позволяващи на потребителите да намират книги въз основа на критерии като автор, жанр или дата на публикуване.
    Управление на потребителски профили: Осигуряване функционалност за потребителите да управляват своите профили, включително актуализиране на лична информация, промяна на пароли и преглед на хронологията на заемите, специфична за всеки потребител.
    Създаване на акаунти: Изглед, в който нов потребител да може да се регистрира за да може да използва приложението.
    Интеграция с външни системи: Приложението може да се интегрира с външни библиотечни системи или бази данни, за да се получи централизирано управление на всичките книги, актуализации на наличността в реално време и безпроблемна интеграция с други процеси на управление на библиотеката. Дори и да е свършила от една библиотека да може безпроблемно да вземе от друга.

Library Management App

The Library Management App is an Android application designed to facilitate the borrowing and returning of books in a library. The app provides a user-friendly interface for managing book transactions and keeping track of the borrowing history.
Features

The Library Management App offers the following features:

    User Authentication: Users can log in to the app using their username and password. The app verifies the credentials against a user database stored in the res/raw/users.txt file.

    Start Menu: After logging in, users are directed to the Start Menu. This menu displays the current user's name and provides options to perform various actions.

    Borrow Book: In the Borrow Book section, users can browse the available books. The app reads the book data from the res/raw/books.txt file, which includes information such as the book ID, name, author, user ID, availability status, and date of borrowing. Users can borrow a book by entering its ID. The app checks if the book is available (isTaken = 0) and verifies that the entered ID matches an available book. If the conditions are met, the app updates the book record by assigning the user's ID, marking it as taken (isTaken = 1), and recording the date of borrowing. The updated book data is then saved to the res/raw/books.txt file.

    Return Book: The Return Book section allows users to view the books they have borrowed. The app retrieves the user's borrowing history by searching for book records with the user's ID. Users can return a book by entering its ID. The app verifies that the entered ID matches a book the user has borrowed. If the book is found, the app updates the book record by marking it as available (isTaken = 0), removing the user's ID, and clearing the date of borrowing. The updated book data is then saved to the books.txt file.

    History of Books: The History of Books section displays the borrowing history. The app reads the transaction records from the res/raw/history.txt file, which includes the transaction ID, user ID, book ID, and the date of borrowing. This section provides a chronological view of all past book transactions.

File Structure

The Library Management App utilizes the following files:

    res/raw/users.txt: This file stores the username and password fields for user authentication. Each line in the file represents a user and follows the format: username,password.

    res/raw/books.txt: Acts as a backup if the books need to be restored.
    
    data/user/0/com.example.libraryapp/files/userX.txt: The books.txt file stores the book records. Each line represents a book and follows the format: id,name,author,userID,isTaken,dateTaken. The isTaken field indicates the availability status of the book (0 for available, 1 for taken). If the book is taken, the userID field stores the ID of the user who borrowed it, and the dateTaken field records the date of borrowing.
    
    data/user/0/com.example.libraryapp/files/userX.txt: This file maintains the borrowing history. Each line represents a transaction and follows the format: userID,bookID,date. The transactionID uniquely identifies each transaction, the userID represents the user who borrowed the book, the bookID represents the borrowed book, and the date indicates the date of borrowing.

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
