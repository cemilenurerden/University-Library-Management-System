package Views;
import Models.NotificationManager;
import Models.StudentModel;
import Models.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class NotificationsView extends JFrame {

    public NotificationsView(UserModel user) {
        setTitle("Bildirimler");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Bildirimleri gösteren alan
        JTextArea notificationArea = new JTextArea();
        notificationArea.setEditable(false);

        // Kullanıcının bildirimlerini NotificationManager'dan al
        List<String> notifications = NotificationManager.getNotifications(user.getLibraryId());
        if (notifications.isEmpty()) {
            notificationArea.setText("Hiç yeni bildirim yok.");
        } else {
            notifications.forEach(notification -> notificationArea.append(notification + "\n"));
        }

        JScrollPane scrollPane = new JScrollPane(notificationArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
