package Repositories;

import Models.BookModel;
import Models.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookRepository implements IBookRepository{

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
            return rowsAffected > 0 ? "Kitap başarıyla eklendi." : "Kitap eklenirken hata oluştu.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Kitap eklenirken bir hata meydana geldi: " + e.getMessage();
        }
    }

    public String deleteBook(String id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Kitap başarıyla silindi." : "Kitap silinirken hata oluştu.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Kitap silinirken bir hata meydana geldi: " + e.getMessage();
        }
    }

    public String getBookIdByTitle(String title) {
        String query = "SELECT id FROM books WHERE title = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("id");
            } else {
                return "Bu başlıkta bir kitap bulunamadı.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Kitap ID alınırken bir hata meydana geldi: " + e.getMessage();
        }
    }
}
