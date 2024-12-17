package Views;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    public RegisterView() {
        setTitle("Kayıt Ol");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        // Kullanıcı kayıt alanları
        JLabel nameLabel = new JLabel("Ad Soyad:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Kayıt Ol");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("© 2024 Kütüphane Yönetim Sistemi", SwingConstants.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }
}
