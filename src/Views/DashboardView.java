package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardView extends JFrame {
    public DashboardView() {
        // Pencere ayarları
        setTitle("Kitap Türü Seçimi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menü çubuğunu ekle
        setJMenuBar(MenuBarHelper.createMenu(this));

        // Ana panel
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel titleLabel = new JLabel("Kitap Türü Seçin", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JButton audioBookButton = new JButton("Sesli Kitaplar");
        JButton eBookButton = new JButton("eBook'lar");
        JButton printedBookButton = new JButton("Baskılı Kitaplar");

        panel.add(audioBookButton);
        panel.add(eBookButton);
        panel.add(printedBookButton);
        add(panel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("© 2024 Kütüphane Yönetim Sistemi", SwingConstants.CENTER);
        add(footerLabel, BorderLayout.SOUTH);

        // Butonlara ActionListener ekleme
        audioBookButton.addActionListener(e -> new BookListView("Sesli Kitaplar").setVisible(true));
        eBookButton.addActionListener(e -> new BookListView("eBook'lar").setVisible(true));
        printedBookButton.addActionListener(e -> new BookListView("Baskılı Kitaplar").setVisible(true));
    }
}
