package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LibrarianView extends JFrame {

    public LibrarianView() {
        // Pencere Ayarları
        setTitle("Kütüphaneci Ana Sayfası");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Başlık
        JLabel titleLabel = new JLabel("Kütüphaneci Ana Sayfası", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Butonlar Paneli
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton addBookButton = createStyledButton("Kitap Ekle");
        JButton viewAllBooksButton = createStyledButton("Tüm Kitapları Görüntüle");
        JButton viewBorrowedBooksButton = createStyledButton("Ödünç Alınan Kitapları Gör");

        buttonPanel.add(addBookButton);
        buttonPanel.add(viewAllBooksButton);
        buttonPanel.add(viewBorrowedBooksButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Çıkış Butonu
        JButton logoutButton = createStyledButton("Çıkış Yap");
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(new Color(245, 245, 245));
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // ActionListener'lar
        addBookButton.addActionListener(e -> {
            new AddBookView().setVisible(true);
            dispose();
        });



        viewAllBooksButton.addActionListener(e -> {
            new BookListView(null, -1).setVisible(true); // Tüm kitapları görüntüle
            dispose();
        });

        viewBorrowedBooksButton.addActionListener(e -> {
            new BorrowedBooksView().setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Çıkış yapmak istediğinizden emin misiniz?",
                    "Çıkış Yap", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new MainScreenView().setVisible(true);
                dispose();
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarianView().setVisible(true));
    }
}
