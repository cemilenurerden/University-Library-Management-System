package Views;

import Controllers.UserController;
import Models.StudentModel;
import Models.UserModel;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {

    private UserController userController = new UserController();

    public RegisterView() {
        setTitle("Kayıt Ol");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık
        JLabel titleLabel = new JLabel("Kayıt Ol", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));

        // Form Paneli
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(255, 255, 255));

        // Form Alanları
        JLabel nameLabel = new JLabel("Ad:");
        JTextField nameField = createTextField();
        JLabel surnameLabel = new JLabel("Soyad:");
        JTextField surnameField = createTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = createTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = createPasswordField();
        JLabel libraryIdLabel = new JLabel("Kütüphane ID:");
        JTextField libraryIdField = createTextField();

        // Kayıt Ol Butonu
        JButton registerButton = new JButton("Kayıt Ol");
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Form Bileşenlerini Ekleyelim
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(surnameLabel);
        formPanel.add(surnameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(libraryIdLabel);
        formPanel.add(libraryIdField);

        // Buton Paneli
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(registerButton);

        // Ana Paneli Düzenleyelim
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Kayıt Ol Butonu Olayı
        registerButton.addActionListener(e -> {
            String firstName = nameField.getText();
            String lastName = surnameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String libraryId = libraryIdField.getText();

            // Giriş Kontrolleri
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || libraryId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurunuz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kullanıcıyı Veritabanına Kaydet
            UserModel newUser = new StudentModel(email, password, libraryId, firstName, lastName);
            boolean isRegistered = userController.register(newUser);

            if (isRegistered) {
                JOptionPane.showMessageDialog(this, "Kayıt başarılı! Giriş yapabilirsiniz.");
                new LoginView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Kayıt başarısız. Lütfen tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        return passwordField;
    }


}
