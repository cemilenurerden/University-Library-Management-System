package Views;

import Controllers.BookController;
import Controllers.BorrowController;
import Models.BookModel;
import Models.StudentModel;
import Models.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserInfoView extends JFrame {

    private BorrowController borrowController = new BorrowController();
    private UserModel user;

    public UserInfoView(UserModel user) {
        this.user = user;
        // Pencere Ayarları
        setTitle("Kullanıcı Bilgileri ve Ödünç Alınan Kitaplar");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header Paneli
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Kullanıcı Bilgileri", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        // Header Butonları
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton backButton = new JButton("Geri");
        JButton profileButton = new JButton("Profilim");
        JButton logoutButton = new JButton("Çıkış");

        styleButton(backButton);
        styleButton(profileButton);
        styleButton(logoutButton);

        buttonPanel.add(backButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Kullanıcı Bilgileri Paneli
        JPanel userInfoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        userInfoPanel.setBackground(Color.WHITE);

        JLabel idLabel = new JLabel("Kütüphane ID: " + user.getLibraryId());
        JLabel nameLabel = new JLabel("Ad Soyad: " + user.getFirstName() + " " + user.getLastName());
        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        JLabel roleLabel = new JLabel("Rol: " + user.getRole());

        styleLabel(idLabel);
        styleLabel(nameLabel);
        styleLabel(emailLabel);
        styleLabel(roleLabel);

        userInfoPanel.add(idLabel);
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(emailLabel);
        userInfoPanel.add(roleLabel);

        // Kitap Listeleme Paneli
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
        bookPanel.setBorder(BorderFactory.createTitledBorder("Ödünç Alınan Kitaplar"));

        // Dinamik Kitap Verilerini Çek
        List<BookModel> borrowedBooks = borrowController.getUserBorrowedBooks(user.getEmail());

        for (BookModel book : borrowedBooks) {
            JPanel bookItem = createBookItem(book.title, book.author, book.description);
            bookPanel.add(bookItem);
        }

        JScrollPane bookScrollPane = new JScrollPane(bookPanel);
        bookScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Ana Paneli Birleştir
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(userInfoPanel, BorderLayout.CENTER);
        mainPanel.add(bookScrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // Buton İşlevleri
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true);
            dispose();
        });

        profileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Zaten bu sayfadasınız.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        });

        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Çıkış yapmak istediğinize emin misiniz?", "Çıkış Yap", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dispose();
                new MainScreenView().setVisible(true);
            }
        });
    }

    // Kitap Kartı Oluşturma
    private JPanel createBookItem(String bookName, String author, String date) {
        JPanel bookItem = new JPanel(new BorderLayout());
        bookItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bookItem.setBackground(Color.WHITE);

        JLabel bookInfo = new JLabel("<html><b>" + bookName + "</b> - " + author + "<br>Ödünç Alma Tarihi: " + date + "</html>");
        bookInfo.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton returnButton = new JButton("Geri Ver");
        styleButton(returnButton);

        // Geri Ver Butonu İşlevi
        returnButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, bookName + " geri verildi!");
            borrowController.returnBorrowedBook(bookName);
            bookItem.setVisible(false);
        });

        bookItem.add(bookInfo, BorderLayout.CENTER);
        bookItem.add(returnButton, BorderLayout.EAST);

        return bookItem;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(new Color(44, 62, 80));
    }
}
