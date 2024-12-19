package Views;

import Controllers.BorrowController;
import Models.BorrowedBookModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BorrowedBooksView extends JFrame {

    private BorrowController borrowController;

    public BorrowedBooksView(String role) {
        borrowController = new BorrowController();

        // Pencere Ayarları
        setTitle("Ödünç Alınan Kitaplar");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Ödünç Alınan Kitaplar", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JButton backButton = createStyledButton("Geri Dön");
        JButton logoutButton = createStyledButton("Çıkış");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Tablo Verileri
        List<BorrowedBookModel> borrowedBooks = borrowController.getAllBorrowedBooks();
        String[] columnNames = {"Kitap ID", "Kitap Adı", "Yazar", "Ödünç Alan", "Tarih"};
        Object[][] data = new Object[borrowedBooks.size()][5];

        for (int i = 0; i < borrowedBooks.size(); i++) {
            BorrowedBookModel book = borrowedBooks.get(i);
            data[i][0] = book.getBookId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getBorrowedBy();
            data[i][4] = book.getBorrowDate();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // Ana Paneli Birleştir
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        // Geri Dön Butonu
        backButton.addActionListener(e -> {
            new LibrarianView(role).setVisible(true);
            dispose();
        });

        // Çıkış Butonu
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Çıkış yapmak istediğinizden emin misiniz?", "Çıkış", JOptionPane.YES_NO_OPTION);
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
        return button;
    }
}
