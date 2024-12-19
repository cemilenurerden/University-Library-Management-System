package Views;

import javax.swing.*;
import java.awt.*;

public class MainScreenView extends JFrame {

    public MainScreenView() {
        setTitle("Üniversite Kütüphane Sistemi");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Arka plan rengi

        // Hoşgeldiniz Mesajı
        JLabel welcomeLabel = new JLabel("Üniversite Kütüphane Sistemine Hoşgeldiniz", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(44, 62, 80));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Üst boşluk

        // Buton Paneli (Orta kısım)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1), // İnce kenarlık
                BorderFactory.createEmptyBorder(20, 50, 20, 50) // İç boşluklar
        ));

        // Butonlar
        JButton loginButton = createStyledButton("Giriş Yap", new Color(41, 128, 185));
        JButton registerButton = createStyledButton("Kayıt Ol", new Color(39, 174, 96));

        // Butonları panelin ortasına ekle
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Butonlar arası boşluk
        gbc.gridy = 0;
        buttonPanel.add(loginButton, gbc);
        gbc.gridy = 1;
        buttonPanel.add(registerButton, gbc);

        // Footer (Alt mesaj)
        JLabel footerLabel = new JLabel("© 2024 Kütüphane Yönetim Sistemi", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(127, 140, 141));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Ana panele bileşenleri ekle
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        // Frame'e ana paneli ekle
        add(mainPanel);

        // Buton Olayları
        loginButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        registerButton.addActionListener(e -> {
            new RegisterView().setVisible(true);
            dispose();
        });
    }

    // Butonlara stil vermek için yardımcı metot
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }


}
