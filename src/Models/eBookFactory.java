package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class eBookFactory implements BookFactory{

    @Override
    public BookModel createBook(String title, String author, String genre_name, String description, Date publication_year, String file_url, String status) {
        return new eBookModel(title,author,genre_name,description,publication_year,file_url,status);
    }

    @Override
    public String updateBook(BookModel book, String id) {
        String query = "UPDATE books SET title = ?, author = ?, genre_name = ?, description = ?, " +
                "publication_year = ?, file_url = ?, status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.title);
            preparedStatement.setString(2, book.author);
            preparedStatement.setString(3, book.genre_name);
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

    @Override
    public String insertBook(BookModel book) {
        String query = "INSERT INTO books (title, author, genre_name, description, publication_year, file_url, status, type) VALUES (?, ?, ?, ?, ?, ?, ?, 'EBook')";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.title);
            preparedStatement.setString(2, book.author);
            preparedStatement.setString(3, book.genre_name);
            preparedStatement.setString(4, book.description);
            preparedStatement.setDate(5, new java.sql.Date(book.publication_year.getTime()));
            preparedStatement.setString(6, book.file_url);
            preparedStatement.setString(7, book.status);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Kitap başarıyla eklendi." : "Kitap eklenirken hata oluştu.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Kitap eklenirken bir hata meydana geldi: " + e.getMessage();
        }
    }

    @Override
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

    @Override
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
