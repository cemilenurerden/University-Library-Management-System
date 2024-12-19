# Library Management System

This is a Java-based **Library Management System** designed to handle core functionalities such as book addition, deletion, searching, borrowing, and notification management. The system is built using multiple software design patterns to ensure scalability, modularity, and maintainability.

---

## **Features**

1. **User Roles**:

   - **Librarian**: Manages book inventory (add, delete, view all books).
   - **Student**: Borrows books and views notifications for library updates.

2. **Book Management**:

   - Add new books with details like title, author, genre, and category.
   - View all books or filter by type.
   - Delete books (Librarian only).

3. **Search Functionality**:

   - Search books by title, author, or category.

4. **Notifications**:

   - Students receive notifications when new books are added.

5. **Dynamic GUI**:

   - User-friendly interfaces for both librarians and students using Java Swing.

---

## **Technologies Used**

- **Java **: Core development language.
- **Java Swing**: For GUI design and interaction.
- **MySQL**: Backend database for storing user, book, and borrowing information.
- **Design Patterns**: Observer, Proxy, State, Abstract Factory, Singleton.

---

## **Design Patterns Implemented**

### **1. Observer Design Pattern**

- **Purpose**: Notify students when new books are added to the library.
- **Implementation**:
  - **Subject**: `BookRepository` notifies observers (students).
  - **Observer**: `StudentModel` receives notifications.
  - **Usage**: When a librarian adds a new book, all registered students are notified dynamically.

### **2. Proxy Design Pattern**

- **Purpose**: Restrict access to specific functionalities based on user roles.
- **Implementation**:
  - **Proxy**: `BookControllerProxyImpl` ensures only librarians can add or delete books.
  - **Real Object**: `BookController` performs actual operations.
  - **Usage**: Librarian actions like adding or deleting books are routed through the proxy.

### **3. State Design Pattern**

- **Purpose**: Manage the state of books (e.g., Available, Borrowed).
- **Implementation**:
  - **State Interface**: `BookState` defines the behaviors for different states.
  - **Concrete States**: `AvailableState`, `BorrowedState`.
  - **Context**: `BookModel` transitions between states dynamically.
  - **Usage**: When a book is borrowed or returned, its state is updated accordingly.

### **4. Abstract Factory Design Pattern**

- **Purpose**: Provide an interface to create families of related book objects.
- **Implementation**:
  - **Abstract Factory**: `BookFactory`.
  - **Concrete Factories**: `eBookFactory`, `PrintedBookFactory`, `VoidBookFactory`.
  - **Usage**: Ensures scalability when adding new book types.

### **5. Singleton Design Pattern**

- **Purpose**: Manage a single instance of the database connection throughout the application.
- **Implementation**:
  - **Class**: `DatabaseConnection`.
  - **Usage**: All repository classes use a single instance of the database connection.

---

## **Setup Instructions**

### **1. Prerequisites**

- Java Development Kit (JDK) 11 or higher.
- MySQL Database.
- An IDE like IntelliJ IDEA or Eclipse.

### **2. Database Setup**

1. Create a database named `librarysystem`.
2. Run the following SQL scripts to create the required tables:
   ```sql
      CREATE TABLE  users (
        id INT NOT NULL AUTO_INCREMENT,
        first_name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100)  NOT NULL,
        library_id VARCHAR(10) NOT NULL,
        email VARCHAR(100) NOT NULL,
        password VARCHAR(255) NOT NULL,
        role ENUM('student','librarian') NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (id),
        UNIQUE KEY library_id (library_id),
        UNIQUE KEY email (email)
      );

      CREATE TABLE  books (
        id INT NOT NULL AUTO_INCREMENT,
        title VARCHAR(255)  NOT NULL,
        author VARCHAR(255)  NOT NULL,
        genre_id INT DEFAULT NULL,
        description TEXT ,
        publication_year DATE DEFAULT NULL,
        category_id INT DEFAULT NULL,
        available_copies INT DEFAULT '0',
        file_url VARCHAR(255)  DEFAULT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        status ENUM('Available','Borrowed','Lost')  DEFAULT 'Available',
        PRIMARY KEY (id),
        KEY genre_id (genre_id),
        KEY category_id (category_id)
      );

      CREATE TABLE borrow_records (
        id INT NOT NULL AUTO_INCREMENT,
        user_id INT NOT NULL,
        book_id INT NOT NULL,
        borrow_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        return_date DATE DEFAULT NULL,
        status ENUM('borrowed','returned')  DEFAULT 'borrowed',
        PRIMARY KEY (id),
        KEY user_id (user_id),
        KEY book_id (book_id)
      ); 

      CREATE TABLE  categories (
        id INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(100)  NOT NULL,
        PRIMARY KEY (id),
        UNIQUE KEY name (name)
      );

      CREATE TABLE  genres (
        id INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(100)   NOT NULL,
        PRIMARY KEY (id),
        UNIQUE KEY name (name)
      );
  
   ```

### **3. Running the Project**

1. Clone the repository or download the project files.
2. Open the project in your preferred IDE.
3. Configure the database connection in `DatabaseConnection` class:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/librarysystem";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "your_password";
   ```
4. Run the `MainScreenView` class to start the application.

---

## **Usage Guide**

### **Librarian**

1. Login as a librarian.
2. Add, view, and delete books.
3. View borrowed books.

### **Student**

1. Login as a student.
2. Borrow available books.
3. View notifications about newly added books.

---




