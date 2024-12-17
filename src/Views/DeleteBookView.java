package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteBookView extends JFrame {
    public DeleteBookView() {
        // Pencere ayarları
        setTitle("Kitap Sil");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana panel
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Kitap ID veya Adı
        JLabel bookIdLabel = new JLabel("Kitap ID / Adı:");
        JTextField bookIdField = new JTextField();
        JButton deleteButton = new JButton("Sil");

        panel.add(bookIdLabel);
        panel.add(bookIdField);
        panel.add(new JLabel()); // Boşluk eklemek için
        panel.add(deleteButton);

        add(panel);

        // Silme butonu işlemi
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText();
                if (!bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kitap Silindi: " + bookId, "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    new LibrarianView().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen kitap ID veya adını girin.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
