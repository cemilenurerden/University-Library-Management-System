package Views;

import Controllers.BookController;
import Controllers.BorrowController;
import Models.BookModel;
import Models.UserModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BookListView extends JFrame {
    private BookController bookController;
    private JPanel contentPanel;
    private BorrowController borrowController ;
    private UserModel user ;


    public BookListView(UserModel user, int id) {
        bookController = new BookController();
        borrowController = new BorrowController();
        this.user =user;
        // Pencere Ayarları
        setTitle("Kitap Listesi");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

// Başlık
        JLabel titleLabel = new JLabel("Kitaplar".toUpperCase(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

// Bildirim ve Diğer Butonlar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton notificationsButton = new JButton("Bildirimler");
        JButton profileButton = new JButton("Profilim");
        JButton logoutButton = new JButton("Çıkış");
        JButton backButton = new JButton("Geri");

        styleButton(notificationsButton);
        styleButton(profileButton);
        styleButton(logoutButton);
        styleButton(backButton);

        if(id==-1){
            buttonPanel.add(backButton);
            buttonPanel.add(logoutButton);

        }
        else {
// Butonları ekle
            buttonPanel.add(backButton);
            buttonPanel.add(notificationsButton); // Bildirim butonunu ekle
            buttonPanel.add(profileButton);
            buttonPanel.add(logoutButton);
        }
// Header Paneli Birleştir
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

// Bildirim Butonu İşlevi
        notificationsButton.addActionListener(e -> {
            new NotificationsView(user).setVisible(true); // Bildirim ekranını aç
        });

        // Kitap Listesi İçin Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);

        // Arama Alanı
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Ara");
        styleButton(searchButton);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Kitap Listesini Göster
        displayBookList(id);

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                displaySearchResults(query);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir arama terimi girin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Ana Paneli Birleştir
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Buton İşlevleri
        profileButton.addActionListener(e -> {
            new UserInfoView(user).setVisible(true); // Profil sayfasına geç
            dispose();
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Çıkış yapmak istediğinize emin misiniz?", "Çıkış", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new MainScreenView().setVisible(true); // Ana ekrana dön
                dispose();
            }
        });
    if(id==-1){
        backButton.addActionListener(e -> {
            new LibrarianView("librarian").setVisible(true); // Dashboard sayfasına geri dön
            dispose();
        });
    }
    else{
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true); // Dashboard sayfasına geri dön
            dispose();
        });
    }
    }


    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
    }

    private void displayBookList(int id) {
        contentPanel.removeAll();
        List<BookModel> books;

        // Kitapları getirme
        if (id == -1) {
            books = bookController.getAllBooks(); // Tüm kitaplar
        } else {
            books = bookController.getBooksByType(id); // Belirli türe göre kitaplar
        }

        if (books.isEmpty()) {
            contentPanel.add(new JLabel("Bu türde kitap bulunamadı.", SwingConstants.CENTER));
        } else {
            for (BookModel book : books) {
                JPanel bookPanel = new JPanel(new BorderLayout());
                bookPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                bookPanel.setBackground(Color.WHITE);
                bookPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                JLabel bookLabel = new JLabel("Kitap Adı: " + book.getTitle() + " | Yazar: " + book.getAuthor());
                bookLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

                // Eğer id == -1 ise Sil butonu, aksi halde Ödünç Al butonu
                if (id == -1) {
                    JButton deleteButton = new JButton("Sil");
                    styleButton(deleteButton);
                    deleteButton.addActionListener(e -> {
                        // Silme işlemi
                        int confirm = JOptionPane.showConfirmDialog(this, book.getTitle() + " kitabını silmek istediğinize emin misiniz?", "Sil", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            bookController.deleteBook(book.getId());
                            JOptionPane.showMessageDialog(this, book.getTitle() + " başarıyla silindi!");
                            bookPanel.setVisible(false); // Paneli kaldır
                        }
                    });
                    bookPanel.add(deleteButton, BorderLayout.EAST);
                } else {
                    JButton borrowButton = new JButton("Ödünç Al");
                    styleButton(borrowButton);
                    borrowButton.addActionListener(e -> {
                        // Ödünç alma işlemi
                        String result = borrowController.borrowBook(user.getEmail(), Integer.parseInt(book.getId()));
                        if (result.contains("başarıyla")) {
                            JOptionPane.showMessageDialog(this, book.getTitle() + " ödünç alındı!");
                            bookPanel.setVisible(false); // Kitap ödünç alındıktan sonra paneli kaldır
                        } else {
                            JOptionPane.showMessageDialog(this, "Kitap ödünç alınırken bir hata oluştu: " + result, "Hata", JOptionPane.ERROR_MESSAGE);
                        }
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
