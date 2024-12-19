package Views;

import Controllers.UserController;
import Models.UserModel;


import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private UserController userController = new UserController();

    public LoginView() {
        setTitle("Giriş Yap");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık
        JLabel titleLabel = new JLabel("Giriş Yap", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));

        // Form Paneli
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(new Color(255, 255, 255));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = createTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = createPasswordField();

        // Giriş Butonu
        JButton loginButton = new JButton("Giriş");
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(loginButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Giriş Butonu Olayı
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Controller üzerinden doğrulama yap
            UserModel user = userController.login(email, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Giriş başarılı: " + user.getFirstName());
                        if(user.getRole() == "student"){
                            new DashboardView(user).setVisible(true);
                            dispose();

                        }
                        else{
                            new LibrarianView().setVisible(true);
                            dispose();
                        }
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı e-posta veya şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        textField.setPreferredSize(new Dimension(200, 25));
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        passwordField.setPreferredSize(new Dimension(200, 25));
        return passwordField;
    }


}
