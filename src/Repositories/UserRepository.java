package Repositories;

import Models.DatabaseConnection;
import Models.LibrarianModel;
import Models.StudentModel;
import Models.UserModel;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository{
// kontroller içinde observeer kullanımı şu şekilde
    /*public class LibraryController {
    private UserRepository userRepository = new UserRepository();

    public void addNewBook(BookModel book) {
        // Tüm öğrencileri observer listesine ekle
        List<StudentModel> allStudents = userRepository.getAllStudents();
        BookRepository bookRepository = new BookRepository((List<Observer>) (List<?>) allStudents);

        // Kitap ekleme işlemi
        System.out.println(bookRepository.insertBook(book));
    }
}*/
    public String HashedPassword(String password){
    try {
        var messageDigest = MessageDigest.getInstance("SHA-256");
        var hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        return String.format("%064x", new BigInteger(1, hash));
    }
    catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null;
    }
}

    public List<StudentModel> getAllStudents(){ // bu profilde kullanılmak yerine sadece bildirim için ayarlanabilir
        List<StudentModel> students = new ArrayList<>();
        String query = "SELECT first_name, last_name,library_id,email, password  FROM users WHERE role = 'student'";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String libraryId = resultSet.getString("library_id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");



                // StudentModel nesnesi oluşturup listeye ekle
                students.add(new StudentModel(email, password,libraryId, firstName, lastName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;

    }
    public UserModel getUserByEmailandPassword(String email, String password) {
        password = HashedPassword(password); // Şifreyi hashle
        String selectQuery = "SELECT first_name, last_name, library_id, role FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                String libraryId = resultSet.getString("library_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                if ("student".equalsIgnoreCase(role)) {
                    return new StudentModel(email, password, libraryId, firstName, lastName);
                } else if ("librarian".equalsIgnoreCase(role)) {
                    return new LibrarianModel(email, password, libraryId, firstName, lastName);
                }
            } else {
                System.out.println("Hatalı e-posta veya şifre.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public String getUserIdByEmail(String email) {
        String query = "SELECT id FROM users WHERE email = ?";
        String userId = null;

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getString("id");
            } else {
                System.out.println("Kullanıcı bulunamadı: " + email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Kullanıcı ID'si getirilirken hata oluştu: " + e.getMessage());
        }

        return userId;
    }
    public boolean insertUser(UserModel user){
        String password = HashedPassword(user.getPassword());

        String insertQuery = "INSERT INTO users (first_name, last_name,library_id, email, password, role) VALUES (?, ?, ?, ?,?,?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Değerleri ayarla
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLibraryId());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, user.getRole());

            // Sorguyu çalıştır
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String email) {
        String query = "DELETE FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateUser(String email, String newFirstName) {
        String query = "UPDATE users SET first_name = ? WHERE email = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newFirstName);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
