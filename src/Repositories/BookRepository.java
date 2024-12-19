package Repositories;

import Models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepository implements  Subject {
    private UserRepository userRepository = new UserRepository();




    // tüm veri tabanı işlevleri burada olucak
    public String updateBook(BookModel book, String id) {
        String query = "UPDATE books SET title = ?, author = ?, genre_id = ?, description = ?, " +
                "publication_year = ?, file_url = ?, status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.title);
            preparedStatement.setString(2, book.author);
            preparedStatement.setInt(3, book.genre_id);
            preparedStatement.setString(4, book.description);
            preparedStatement.setDate(5, new java.sql.Date(book.publication_year.getTime()));
            preparedStatement.setString(6, book.file_url);
            preparedStatement.setString(7, book.status);
            preparedStatement.setString(8, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Kitap başarıyla güncellendi." : "Kitap güncellenirken hata oluştu.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Kitap güncellenirken bir hata meydana geldi: " + e.getMessage();
        }
    }

    public String insertBook(BookModel book) {
        String query = "INSERT INTO books ( `title`, `author`, `genre_id`, `description`, `publication_year`, `category_id`, `available_copies`, `file_url`, status) VALUES (?, ?, ?, ?, ?, 1,2, ?,?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.title);
            preparedStatement.setString(2, book.author);
            preparedStatement.setInt(3, book.genre_id);
            preparedStatement.setString(4, book.description);
            java.sql.Date sqlDate = new java.sql.Date(book.publication_year.getTime());
            preparedStatement.setDate(5, sqlDate);
            preparedStatement.setString(6, book.file_url);
            preparedStatement.setString(7, book.status);

            int rowsAffected = preparedStatement.executeUpdate();

            // Bildirim ekle
            for (StudentModel student : userRepository.getAllStudents()) {
                NotificationManager.addNotification(student.getLibraryId(), "Yeni kitap eklendi: " + book.title);
            }


            return rowsAffected > 0 ? "Kitap başarıyla eklendi." : "Kitap eklenirken hata oluştu.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Kitap eklenirken bir hata meydana geldi: " + e.getMessage();
        }
    }
    // Tür listesini getir (genre)
    public Map<String, Integer> getGenres() {
        String query = "SELECT id, name FROM genres";
        Map<String, Integer> genres = new HashMap<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                genres.put(resultSet.getString("name"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Türler alınırken hata oluştu: " + e.getMessage());
        }

        return genres;
    }

    // Kategori listesini getir (category)
    public Map<String, Integer> getCategories() {
        String query = "SELECT id, name FROM categories";
        Map<String, Integer> categories = new HashMap<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                categories.put(resultSet.getString("name"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Kategoriler alınırken hata oluştu: " + e.getMessage());
        }

        return categories;
    }
    public List<BookModel> getBooksByType(int categoryId) {
        List<BookModel> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE category_id = ? AND status = 'Available'";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, categoryId); // Parametre olarak gelen ID'yi kullan

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BookFactory factory;

                // Kitap kategorisine göre uygun Factory seçimi
                switch (categoryId) {
                    case 1:
                        factory = new VoidBookFactory();
                        break;
                    case 2:
                        factory = new eBookFactory();
                        break;
                    case 3:
                        factory = new PrintedBookFactory();
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
                        resultSet.getString("status")
                );
                book.setId(resultSet.getString("id")); // Kitap ID'si atanıyor
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Kitaplar alınırken hata oluştu: " + e.getMessage());
        }

        return books;
    }

    public String deleteBook(String bookId) {
        String deleteBorrowRecordsQuery = "DELETE FROM borrow_records WHERE book_id = ?";
        String deleteBookQuery = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement deleteBorrowRecordsStmt = connection.prepareStatement(deleteBorrowRecordsQuery);
             PreparedStatement deleteBookStmt = connection.prepareStatement(deleteBookQuery)) {

            // Önce borrow_records tablosundaki kayıtları sil
            deleteBorrowRecordsStmt.setString(1, bookId);
            deleteBorrowRecordsStmt.executeUpdate();

            // Ardından books tablosundaki kitabı sil
            deleteBookStmt.setString(1, bookId);
            int rowsAffected = deleteBookStmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Kitap ve ilgili ödünç kayıtları başarıyla silindi.";
            } else {
                return "Kitap silinirken bir hata oluştu veya kitap bulunamadı.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Silme işlemi sırasında hata oluştu: " + e.getMessage();
        }
    }


    public List<BookModel> getAllBooks() {
        List<BookModel> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookFactory factory;
                String category = resultSet.getString("category_id");

                // Factory'yi kategoriye göre seç
                switch (category) {
                    case "1":
                        factory = new eBookFactory();
                        break;
                    case "2":
                        factory = new PrintedBookFactory();
                        break;
                    case "3":
                        factory = new VoidBookFactory();
                        break;
                    default:
                        throw new IllegalArgumentException("Bilinmeyen kategori ID: " + category);
                }

                // Factory ile kitabı oluştur
                BookModel book = factory.createBook(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("genre_id"),
                        resultSet.getString("description"),
                        resultSet.getDate("publication_year"),
                        resultSet.getString("file_url"),
                        resultSet.getString("status")
                );
                book.setId(resultSet.getString("id")); // ID ayrı olarak ayarlanıyor
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Kitaplar alınırken bir hata meydana geldi: " + e.getMessage());
        }

        return books;
    }


    public List<BookModel> searchBooks(String query) {
        List<BookModel> books = new ArrayList<>();
        String searchQuery = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BookFactory factory;
                String category = resultSet.getString("category_id");

                // Factory'yi kategoriye göre seç
                switch (category) {
                    case "1":
                        factory = new eBookFactory();
                        break;
                    case "2":
                        factory = new PrintedBookFactory();
                        break;
                    case "3":
                        factory = new VoidBookFactory();
                        break;
                    default:
                        throw new IllegalArgumentException("Bilinmeyen kategori ID: " + category);
                }

                // Factory ile kitabı oluştur
                BookModel book = factory.createBook(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("genre_id"),
                        resultSet.getString("description"),
                        resultSet.getDate("publication_year"),
                        resultSet.getString("file_url"),
                        resultSet.getString("status")
                );
                book.setId(resultSet.getString("id")); // ID ayrı olarak ayarlanıyor
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Arama sırasında bir hata meydana geldi: " + e.getMessage());
        }

        if (!books.isEmpty()) {
            System.out.println("Arama sonuçları başarıyla alındı: " + books.size() + " kitap bulundu.");
        } else {
            System.out.println("Arama sonucu bulunamadı: " + query);
        }

        return books;
    }

}
