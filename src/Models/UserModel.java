package Models;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class UserModel  {
    private String email;
    private String password;
    private String LibraryId;
    private String FirstName;
    private String LastName;
    private String role;


 public UserModel(String email, String password)
    {
        this.email = email;
        this.password = HashedPassword(password);

    }
    public UserModel(String email, String password, String LibraryId,String FirstName, String LastName, String role)
    {
        this.email = email;
        this.password = HashedPassword(password);
        this.LibraryId = LibraryId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.role = role;
    }

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

    public String Login (){
        String selectQuery = "SELECT role from users WHERE email = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            // Değerleri ayarla
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            // Sorguyu çalıştır
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString("role"); // Rol bilgisini al
                return role; // Rolü döndür
            } else {
                System.out.println("Hatalı e-posta veya şifre.");
                return null; // Giriş başarısız
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean Register(){
        String insertQuery = "INSERT INTO users (first_name, last_name,library_id, email, password, role) VALUES (?, ?, ?, ?,?,?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Değerleri ayarla
            preparedStatement.setString(1, FirstName);
            preparedStatement.setString(2, LastName);
            preparedStatement.setString(3, LibraryId);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, role);

            // Sorguyu çalıştır
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getLibraryId() {
        return LibraryId;
    }

    public void setLibraryId(String libraryId) {
        LibraryId = libraryId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
