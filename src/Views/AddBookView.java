package Views;

import javax.swing.*;
import java.awt.*;

public class AddBookView extends JFrame {
    public AddBookView() {
        setTitle("Kitap Ekle");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Kitap Adı:"));
        JTextField titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Yazar:"));
        JTextField authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("Yıl:"));
        JTextField yearField = new JTextField();
        panel.add(yearField);

        panel.add(new JLabel("Tür:"));
        JTextField genreField = new JTextField();
        panel.add(genreField);

        JButton addButton = new JButton("Ekle");
        panel.add(addButton);

        add(panel);

        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Kitap eklendi: " + titleField.getText());
            dispose();
            new LibrarianView().setVisible(true);
        });
    }
}
