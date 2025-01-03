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

// kullanılmımışsa sil

 public UserModel(String email, String password)
    {
        this.email = email;
        this.password = password;

    }
    public UserModel(String email, String password, String LibraryId,String FirstName, String LastName, String role)
    {
        this.email = email;
        this.password = password;
        this.LibraryId = LibraryId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.role = role;
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
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
