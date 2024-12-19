package Views;

import Controllers.BookController;
import Models.BookModel;
import Models.PrintedBookModel;
import Models.VoidBookModel;
import Models.eBookModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Map;

public class AddBookView extends JFrame {
    private JTextField titleField, authorField, descriptionField, yearField,  fileUrlField, statusField;
    private JComboBox<String> genreComboBox, categoryComboBox;
    private Map<String, Integer> genreMap, categoryMap;
    private BookController bookController;

    public AddBookView() {
        bookController = new BookController();

        // Pencere Ayarları
        setTitle("Kitap Ekle");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Kitap Ekle", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JButton backButton = createStyledButton("Geri");
        JButton logoutButton = createStyledButton("Çıkış");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Form Alanları
        titleField = addFormField(formPanel, "Kitap Adı:");
        authorField = addFormField(formPanel, "Yazar:");
        genreComboBox = addComboBoxField(formPanel, "Tür:");
        categoryComboBox = addComboBoxField(formPanel, "Kategori:");
        descriptionField = addFormField(formPanel, "Açıklama:");
        yearField = addFormField(formPanel, "Yıl (yyyy-MM-dd):");
        fileUrlField = addFormField(formPanel, "Dosya URL:");

        JButton addButton = createStyledButton("Ekle");
        JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanelBottom.setBackground(Color.WHITE);
        buttonPanelBottom.add(addButton);

        // Ana Panel Birleştirme
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanelBottom, BorderLayout.SOUTH);

        add(mainPanel);

        // Dinamik Verileri Yükle
        loadGenreData();
        loadCategoryData();

        // Action Listeners
        addButton.addActionListener(e -> insertBook());
        backButton.addActionListener(e -> {
            new LibrarianView().setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Çıkış yapmak istediğinizden emin misiniz?", "Çıkış", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new MainScreenView().setVisible(true);
                dispose();
            }
        });
    }

    private JTextField addFormField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField textField = new JTextField();
        panel.add(jLabel);
        panel.add(textField);
        return textField;
    }

    private JComboBox<String> addComboBoxField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JComboBox<String> comboBox = new JComboBox<>();
        panel.add(jLabel);
        panel.add(comboBox);
        return comboBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 10, 10, 10));
        return button;
    }

    private void loadGenreData() {
        genreMap = bookController.getGenres();
        genreComboBox.removeAllItems();
        genreMap.forEach((name, id) -> genreComboBox.addItem(name));
    }

    private void loadCategoryData() {
        categoryMap = bookController.getCategories();
        categoryComboBox.removeAllItems();
        categoryMap.forEach((name, id) -> categoryComboBox.addItem(name));
    }
    private void insertBook() {
        try {
            // Formdan alanları al
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            int genreId = genreMap.get((String) genreComboBox.getSelectedItem());
            int categoryId = categoryMap.get((String) categoryComboBox.getSelectedItem());
            String description = descriptionField.getText().trim();
            Date publicationYear = Date.valueOf(yearField.getText().trim());

            String fileUrl = fileUrlField.getText().trim();


            // Zorunlu alan kontrolü
            if (title.isEmpty() || author.isEmpty() || description.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kategoriye göre BookModel oluştur
            BookModel book = null;

            if (categoryId == 1) { // E-Kitap
                book = new eBookModel(title, author, genreId, description, publicationYear, fileUrl, "Available");
            } else if (categoryId == 3) { // Basılı Kitap
                book = new PrintedBookModel(title, author, genreId, description, publicationYear, "Raf1", "Available");
            } else if (categoryId == 2) { // Sesli Kitap
                book = new VoidBookModel(title, author, genreId, description, publicationYear, "MP3","Available");
            } else {
                JOptionPane.showMessageDialog(this, "Geçersiz kategori seçildi!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Oluşturulan kitap nesnesini controller'a gönder
            String result = bookController.addBook(book);
            JOptionPane.showMessageDialog(this, result, "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz veri formatı! " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        descriptionField.setText("");
        yearField.setText("");

        fileUrlField.setText("");

        genreComboBox.setSelectedIndex(0);
        categoryComboBox.setSelectedIndex(0);
    }
}
