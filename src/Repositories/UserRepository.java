package Repositories;

import Models.DatabaseConnection;
import Models.StudentModel;
import Models.UserModel;

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
    public List<StudentModel> getAllStudednts(){
        List<StudentModel> students = new ArrayList<>();
        String query = "SELECT email, first_name FROM users WHERE role = 'student'";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("first_name");
                students.add(new StudentModel(email, firstName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;

    }
    public String getUserRoleByEmailandPassword(String email, String password){
        String selectQuery = "SELECT role from users WHERE email = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            // Değerleri ayarla
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            // Sorguyu çalıştır
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role"); // Rol bilgisini al
                return role; // Rolü döndür
            } else {
                System.out.println("Hatalı e-posta veya şifre.");
                return null; // Giriş başarısız
            }
            //   if ("student".equalsIgnoreCase(role)) {
            //                    return new StudentModel(userEmail, firstName);
            //                } else if ("librarian".equalsIgnoreCase(role)) {
            //                    return new LibrarianModel(userEmail, firstName);
            //                }  bununla istediğim tipte kullanıcı için nesne oluşturmuş oluyorum.

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean insertUser(UserModel user){
        String insertQuery = "INSERT INTO users (first_name, last_name,library_id, email, password, role) VALUES (?, ?, ?, ?,?,?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Değerleri ayarla
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLibraryId());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole());

            // Sorguyu çalıştır
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
// bu kısımı kullanıcı bilgilerini getir diye yap

//    public UserModel getAllUsers(){
//        String selectQuery = "SELECT * from users ";
//
//        try (Connection connection = DatabaseConnection.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
//
//            // Değerleri ayarla
//            preparedStatement.setString(1, email);
//            preparedStatement.setString(2, password);
//            // Sorguyu çalıştır
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String role = resultSet.getString("role"); // Rol bilgisini al
//                return role; // Rolü döndür
//            } else {
//                System.out.println("Hatalı e-posta veya şifre.");
//                return null; // Giriş başarısız
//            }
//            //   if ("student".equalsIgnoreCase(role)) {
//            //                    return new StudentModel(userEmail, firstName);
//            //                } else if ("librarian".equalsIgnoreCase(role)) {
//            //                    return new LibrarianModel(userEmail, firstName);
//            //                }  bununla istediğim tipte kullanıcı için nesne oluşturmuş oluyorum.
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
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
