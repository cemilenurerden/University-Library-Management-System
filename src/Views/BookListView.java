package Views;

import javax.swing.*;
import java.awt.*;

public class BookListView extends JFrame {
    public BookListView(String bookType) {
        // Pencere ayarları
        setTitle(bookType + " Listesi");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menü çubuğunu ekle
        setJMenuBar(MenuBarHelper.createMenu(this));

        // Ana panel
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(bookType, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Kitap 1");
        listModel.addElement("Kitap 2");
        listModel.addElement("Kitap 3");

        JList<String> bookList = new JList<>(listModel);
        panel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        JButton borrowButton = new JButton("Ödünç Al");
        panel.add(borrowButton, BorderLayout.SOUTH);

        add(panel);

        borrowButton.addActionListener(e -> {
            String selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {
                JOptionPane.showMessageDialog(this, selectedBook + " ödünç alındı!",
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçin.",
                        "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
