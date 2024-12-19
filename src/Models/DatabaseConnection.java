package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/librarysystem";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        createConnection();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    private void createConnection() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Veritabanı bağlantısı oluşturulamadı.");
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                createConnection(); // Kapalı bağlantıyı yeniden oluştur
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Bağlantı kontrol edilirken hata oluştu.", e);
        }
        return connection;
    }
}
