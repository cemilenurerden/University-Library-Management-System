package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenView extends JFrame {
    public MainScreenView() {
        // Ana pencere ayarları
        setTitle("Kütüphane Yönetim Sistemi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout ve bileşenler
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Kütüphane Yönetim Sistemi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Butonlar
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton loginButton = new JButton("Giriş Yap");
        JButton registerButton = new JButton("Kayıt Ol");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Footer
        JLabel footerLabel = new JLabel("© 2024 Kütüphane Yönetim Sistemi", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Ekrana ekleme
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // Butonlara action ekleme
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView().setVisible(true); // Giriş ekranını aç
                dispose(); // Ana ekranı kapat
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterView().setVisible(true); // Kayıt ekranını aç
                dispose(); // Ana ekranı kapat
            }
        });
    }
}
