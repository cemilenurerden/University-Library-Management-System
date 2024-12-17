package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarHelper {

    public static JMenuBar createMenu(JFrame parentFrame) {
        JMenuBar menuBar = new JMenuBar();

        // Menü Çubuğu
        JMenu menu = new JMenu("Seçenekler");
        JMenuItem viewBookDetails = new JMenuItem("Kitap Bilgilerini Görüntüle");
        JMenuItem logout = new JMenuItem("Çıkış Yap");

        // Kitap Bilgilerini Görüntüle
        viewBookDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentFrame, "Kitap bilgilerini görüntüleme ekranı burada olacak.",
                        "Kitap Bilgileri", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Çıkış Yap
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(parentFrame, "Çıkış yapmak istediğinizden emin misiniz?",
                        "Çıkış Yap", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    parentFrame.dispose(); // Sayfayı kapat
                    new MainScreenView().setVisible(true); // Ana sayfaya dön
                }
            }
        });

        menu.add(viewBookDetails);
        menu.addSeparator(); // Menüde bir çizgi ekler
        menu.add(logout);
        menuBar.add(menu);

        return menuBar;
    }
}
