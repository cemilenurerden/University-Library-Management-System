package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianView extends JFrame {

    public LibrarianView() {
        // Pencere Ayarları
        setTitle("Kütüphaneci Ana Sayfası");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Başlık
        JLabel titleLabel = new JLabel("Kütüphaneci Ana Sayfası", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Butonlar Paneli
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton addBookButton = new JButton("Kitap Ekle");
        JButton deleteBookButton = new JButton("Kitap Sil");
        JButton viewAllBooksButton = new JButton("Tüm Kitapları Görüntüle");
        JButton viewBorrowedBooksButton = new JButton("Ödünç Alınan Kitapları Gör");

        buttonPanel.add(addBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(viewAllBooksButton);
        buttonPanel.add(viewBorrowedBooksButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Çıkış Butonu
        JButton logoutButton = new JButton("Çıkış Yap");
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        // Ana Paneli Ekle
        add(mainPanel);

        // ActionListener'lar
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddBookView().setVisible(true);
                dispose();
            }
        });

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteBookView().setVisible(true);
                dispose();
            }
        });

        viewAllBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookListView("Tüm Kitaplar").setVisible(true);
                dispose();
            }
        });

        viewBorrowedBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrowedBooksView().setVisible(true);
                dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Çıkış yapmak istediğinizden emin misiniz?",
                        "Çıkış Yap", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    new MainScreenView().setVisible(true);
                    dispose();
                }
            }
        });
    }
}
