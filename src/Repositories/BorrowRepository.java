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
    public String borrowBook(int userId, int bookId) {
      //  BookModel book = getBookIdByTitle
        String query = "INSERT INTO borrow_records  (user_id, book_id, status) VALUES (?, ?,  'Borrowed')";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Kitap başarıyla ödünç alındı." : "Kitap ödünç alınırken hata oluştu.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Kitap ödünç alınırken bir hata meydana geldi: " + e.getMessage();
        }
    }
    public List<BorrowedBookModel> getAllBorrowedBooks() {
        List<BorrowedBookModel> borrowedBooks = new ArrayList<>();
        String query = "SELECT b.id AS book_id, b.title AS book_title, b.author AS book_author, " +
                "u.email AS borrowed_by, br.borrow_date AS borrow_date " +
                "FROM borrow_records br " +
                "INNER JOIN books b ON br.book_id = b.id " +
                "INNER JOIN users u ON br.user_id = u.id " +
                "WHERE br.status = 'Borrowed'";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // BorrowedBookModel oluştur
                BorrowedBookModel borrowedBook = new BorrowedBookModel(
                        resultSet.getString("book_id"),
                        resultSet.getString("book_title"),
                        resultSet.getString("book_author"),
                        resultSet.getString("borrowed_by"),
                        resultSet.getDate("borrow_date").toString()
                );

                // Listeye ekle
                borrowedBooks.add(borrowedBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ödünç alınan kitaplar getirilirken hata oluştu: " + e.getMessage());
        }

        return borrowedBooks;
    }


    @Override
    public String returnBook(String name) {
        int id = getIdByName(name);
        System.out.println("Kitap ID: " + id); // ID'nin doğru geldiğini kontrol et.
        String query = "UPDATE borrow_records  SET status = 'Returned', return_date = ? WHERE book_id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Kitap başarıyla iade edildi." : "Kitap iade edilirken hata oluştu.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Kitap iade edilirken bir hata meydana geldi: " + e.getMessage();
        }
    }

    @Override
    public List<BookModel> getBorrowedBooksByUser(String userId) {
        String query = "SELECT books.id, books.title, books.author, books.genre_id, books.description, books.publication_year, books.category_id, books.file_url " +
                "FROM books " +
                "INNER JOIN borrow_records ON books.id = borrow_records.book_id " +
                "WHERE borrow_records.user_id = ? AND borrow_records.status = 'Borrowed';";

        List<BookModel> borrowedBooks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BookFactory factory;
                int categoryId = resultSet.getInt("category_id");

                // category_id'ye göre uygun Factory seçimi
                switch (categoryId) {
                    case 1:
                        factory = new eBookFactory();
                        break;
                    case 2:
                        factory = new PrintedBookFactory();
                        break;
                    case 3:
                        factory = new VoidBookFactory();
                        break;
                    default:
                        throw new IllegalArgumentException("Bilinmeyen kategori ID: " + categoryId);
                }

                // Factory ile kitap oluşturuluyor
                BookModel book = factory.createBook(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("genre_id"),
                        resultSet.getString("description"),
                        resultSet.getDate("publication_year"),
                        resultSet.getString("file_url"),
                        "Available" // Durum sabit örnek olarak verildi
                );

                book.setId(resultSet.getString("id"));
                borrowedBooks.add(book);
            }

            if (borrowedBooks.isEmpty()) {
                System.out.println("Bu kullanıcı için ödünç alınan kitap bulunamadı.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Veritabanından kitaplar alınırken hata oluştu: " + e.getMessage());
        }

        return borrowedBooks;
    }



    @Override
    public boolean isBookBorrowed(String bookId) {
        String query = "SELECT COUNT(*) AS count FROM borrow_records  WHERE book_id = ? AND status = 'Borrowed'";
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
    public int getIdByName(String bookTitle) {
        String query = "SELECT id FROM books WHERE title = ?";
        int bookId = -1;

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookTitle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                bookId = resultSet.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Kitap ID alınırken hata oluştu: " + e.getMessage());
        }

        return bookId;
    }
    @Override
    public List<UserModel> getBorrowersByBook(String bookId) {
        String query = "SELECT users.* FROM users INNER JOIN borrow_records  ON users.id = borrows.user_id WHERE borrows.book_id = ? AND borrows.status = 'Borrowed'";
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
