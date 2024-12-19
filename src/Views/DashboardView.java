package Views;

import Controllers.BookController;
import Models.BookModel;
import Models.UserModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DashboardView extends JFrame {

    private JPanel contentPanel;
    private JTextField searchField;
    private UserModel user;
    private BookController bookController;

    public DashboardView(UserModel user) {
        this.user = user;
        this.bookController = new BookController();
        setTitle("Ana Sayfa");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Üst Header
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header Butonları
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(52, 73, 94));

        JButton profileButton = createHeaderButton("Profilim");
        JButton booksButton = createHeaderButton("Kitaplarım");
        JButton logoutButton = createHeaderButton("Çıkış");

        buttonPanel.add(profileButton);
        buttonPanel.add(booksButton);
        buttonPanel.add(logoutButton);

        // Arama Alanı
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(new Color(52, 73, 94));

        searchField = new JTextField();
        JButton searchButton = new JButton("Ara");
        searchButton.setBackground(new Color(41, 128, 185));
        searchButton.setForeground(Color.WHITE);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // İçerik Paneli
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        displayDefaultButtons();

        // Ana Panel Düzeni
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        add(mainPanel);

        // Arama Butonu Olayı
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                displaySearchResults(query);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir arama terimi girin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Çıkış Butonu Olayı
        logoutButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        // Profil ve Kitaplar Butonları Olayları
        profileButton.addActionListener(e -> {
            new UserInfoView(user).setVisible(true);
            dispose();
        });

        booksButton.addActionListener(e -> {
            new BookListView(user,1).setVisible(true);
            dispose();
        });
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(44, 62, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private JButton createContentButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void displayDefaultButtons() {
        contentPanel.removeAll();

        JButton audioBookButton = createContentButton("Sesli Kitap");
        JButton eBookButton = createContentButton("E-Kitap");
        JButton printedBookButton = createContentButton("Basılı Kitap");

        contentPanel.add(audioBookButton);
        contentPanel.add(eBookButton);
        contentPanel.add(printedBookButton);

        // Butonlara Yönlendirme Eklendi
        audioBookButton.addActionListener(e -> {
            new BookListView(user, 2).setVisible(true);
            dispose();
        });

        eBookButton.addActionListener(e -> {
            new BookListView(user, 1).setVisible(true);
            dispose();
        });

        printedBookButton.addActionListener(e -> {
            new BookListView(user, 3).setVisible(true);
            dispose();
        });

        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
    }
    private void displaySearchResults(String query) {
        contentPanel.removeAll();
        List<BookModel> searchResults = bookController.searchBooks(query);

        if (searchResults.isEmpty()) {
            contentPanel.add(new JLabel("Arama sonucu bulunamadı.", SwingConstants.CENTER));
        } else {
            for (BookModel book : searchResults) {
                JPanel bookPanel = new JPanel(new BorderLayout());
                bookPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                bookPanel.setBackground(Color.WHITE);
                bookPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                JLabel bookLabel = new JLabel("Kitap Adı: " + book.getTitle() + " | Yazar: " + book.getAuthor() + " | Durum: " + book.getStatus());
                bookLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

                if (book.getStatus().equalsIgnoreCase("Available")) {
                    JButton borrowButton = new JButton("Ödünç Al");
                    styleButton(borrowButton);

                    borrowButton.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this, book.getTitle() + " ödünç alındı!");
                    });

                    bookPanel.add(borrowButton, BorderLayout.EAST);
                }

                bookPanel.add(bookLabel, BorderLayout.CENTER);
                contentPanel.add(bookPanel);
            }
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
