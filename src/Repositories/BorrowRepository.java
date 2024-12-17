package Repositories;

import Models.*;
import Models.eBookModel;
import Models.BookModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowRepository implements IBorrowRepository {

    @Override
    public String borrowBook(String userId, String bookId, Date borrowDate, Date returnDate) {
      //  BookModel book = getBookIdByTitle
        String query = "INSERT INTO borrows (user_id, book_id, borrow_date, return_date, status) VALUES (?, ?, ?, ?, 'Borrowed')";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, bookId);
            preparedStatement.setDate(3, new java.sql.Date(borrowDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(returnDate.getTime()));

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Kitap başarıyla ödünç alındı." : "Kitap ödünç alınırken hata oluştu.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Kitap ödünç alınırken bir hata meydana geldi: " + e.getMessage();
        }
    }

    @Override
    public String returnBook(String borrowId) {
        String query = "UPDATE borrows SET status = 'Returned', return_date = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
            preparedStatement.setString(2, borrowId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Kitap başarıyla iade edildi." : "Kitap iade edilirken hata oluştu.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Kitap iade edilirken bir hata meydana geldi: " + e.getMessage();
        }
    }

    @Override
    public List<BookModel> getBorrowedBooksByUser(String userId) {
        String query = "SELECT books.id, books.title, books.author, books.genre_id, books.description, books.publication_year\n" +
                "FROM books\n" +
                "INNER JOIN borrow_records ON books.id = borrow_records.book_id\n" +
                "WHERE borrow_records.user_id = ? AND borrow_records.status = 'Borrowed';";
        List<BookModel> borrowedBooks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bookType = resultSet.getString("type");
                BookModel book;

                if ("ebook".equalsIgnoreCase(bookType)) {
                    book = new eBookModel(
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getInt("genre_id"),
                            resultSet.getString("description"),
                            resultSet.getDate("publication_year"),
                            resultSet.getString("file_url"),
                            resultSet.getString("file_format")
                    );
                } else {
                    book = new PrintedBookModel(
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getInt("genre_id"),
                            resultSet.getString("description"),
                            resultSet.getDate("publication_year"),
                            resultSet.getString("file_url"),
                            resultSet.getString("shelf_location")
                    );
                }

                book.setId(resultSet.getString("id"));
                borrowedBooks.add(book);
            }

            if (borrowedBooks.isEmpty()) {
                System.out.println("Bu kullanıcı hiç kitap almadı.");
                return null; // Veya boş liste dönebiliriz.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowedBooks;
    }




    @Override
    public boolean isBookBorrowed(String bookId) {
        String query = "SELECT COUNT(*) AS count FROM borrows WHERE book_id = ? AND status = 'Borrowed'";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<UserModel> getBorrowersByBook(String bookId) {
        String query = "SELECT users.* FROM users INNER JOIN borrows ON users.id = borrows.user_id WHERE borrows.book_id = ? AND borrows.status = 'Borrowed'";
        List<UserModel> borrowers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                StudentModel user = new StudentModel(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("library_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("role")
                );
                borrowers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowers;
    }
}
