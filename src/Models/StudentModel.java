package Models;

import java.util.ArrayList;
import java.util.List;

public class StudentModel extends UserModel implements Observer{

    private List<String> notifications = new ArrayList<>();

    public StudentModel(String email, String password) {
        super(email, password);
        this.notifications = new ArrayList<>();
    }
    public List<String> getNotifications() {
        return notifications;
    }

    public void addNotification(String notification) {
        getNotifications().add(notification);
        System.out.println("Bildirim eklendi: " + notification); // Debug mesajı

    }
    public void update(String message){
        addNotification(message);    }
    public StudentModel(String email, String password, String LibraryId, String FirstName, String LastName) {
        super(email, password, LibraryId, FirstName, LastName,"student");
    }
    public StudentModel(String email, String password, String LibraryId, String FirstName, String LastName, String role) {
        super(email, password, LibraryId, FirstName, LastName,"student");
    }



}
