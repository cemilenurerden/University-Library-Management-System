package Views;

import javax.swing.*;
import java.awt.*;

public class BorrowedBooksView extends JFrame {
    public BorrowedBooksView() {
        // Pencere ayarları
        setTitle("Ödünç Alınan Kitaplar");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tablo modeli
        String[] columnNames = {"Kitap ID", "Kitap Adı", "Ödünç Alan Kullanıcı", "Tarih"};
        Object[][] data = {
                {"1", "Design Patterns", "Ali Yılmaz", "2024-05-10"},
                {"2", "Refactoring", "Ayşe Kara", "2024-05-15"}
        };

        JTable borrowedTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(borrowedTable);

        // Başlık
        JLabel headerLabel = new JLabel("Ödünç Alınan Kitaplar", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Geri butonu
        JButton backButton = new JButton("Geri Dön");
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {
            new LibrarianView().setVisible(true);
            dispose();
        });
    }
}
