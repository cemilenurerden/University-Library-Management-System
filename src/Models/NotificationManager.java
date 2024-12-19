package Models;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class NotificationManager {
    private static HashMap<String, List<String>> notificationsMap = new HashMap<>();

    // Kullanıcıya bildirim ekleme
    public static void addNotification(String userId, String message) {
        notificationsMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(message);
    }

    // Kullanıcının bildirimlerini alma
    public static List<String> getNotifications(String userId) {
        return notificationsMap.getOrDefault(userId, new ArrayList<>());
    }

    // Kullanıcının bildirimlerini temizleme
    public static void clearNotifications(String userId) {
        notificationsMap.remove(userId);
    }
}

