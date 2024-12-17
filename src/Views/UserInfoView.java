package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInfoView extends JFrame {

    public UserInfoView(String userName, String userEmail) {
        // Pencere ayarları
        setTitle("Kullanıcı Bilgileri ve Ödünç Alınan Kitaplar");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Kullanıcı Bilgileri Paneli
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JLabel nameLabel = new JLabel("Ad Soyad: " + userName, SwingConstants.LEFT);
        JLabel emailLabel = new JLabel("Email: " + userEmail, SwingConstants.LEFT);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(emailLabel);

        // Ödünç Alınan Kitaplar Tablosu
        String[] columnNames = {"Kitap Adı", "Yazar", "Tarih"};
        Object[][] data = {
                {"Design Patterns", "Erich Gamma", "2024-06-01"},
                {"Clean Code", "Robert C. Martin", "2024-05-20"},
                {"Refactoring", "Martin Fowler", "2024-05-25"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable bookTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookTable);

        // Buton Paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Geri Dön");
        JButton logoutButton = new JButton("Çıkış Yap");

        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Tüm bileşenleri ana panele ekle
        mainPanel.add(userInfoPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Butonlara ActionListener ekleme
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DashboardView().setVisible(true); // Ana sayfaya geri dön
                dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Çıkış yapmak istediğinize emin misiniz?", "Çıkış Yap", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    new MainScreenView().setVisible(true); // Ana ekrana dön
                    dispose();
                }
            }
        });
    }
}
