package Controllers;

import Models.StudentModel;
import Models.UserModel;
import Repositories.UserRepository;

import java.util.List;

public class UserController {
    private UserRepository userRepository = new UserRepository();

    public UserModel login(String email, String password) {

        UserModel user = userRepository.getUserByEmailandPassword(email, password);
        if (user != null) {
            System.out.println("Giriş başarılı: " );
        } else {
            System.out.println("Giriş başarısız: E-posta veya şifre hatalı.");
        }
        return user;
    }

    public boolean register(UserModel user) {
        boolean success = userRepository.insertUser(user);
        if (success) {
            System.out.println("Kayıt başarılı: " + user.getFirstName());
            return true;
        } else {
            System.out.println("Kayıt başarısız.");
            return false;
        }
    }

    public UserModel profile(String email) {
        List<StudentModel> students = userRepository.getAllStudents();
        for (StudentModel student : students) {
            if (student.getEmail().equals(email)) {
                return student; // Eşleşen kullanıcıyı döner
            }
        }
        return null; // Kullanıcı bulunamazsa null döner
    }
    public void updateProfile(String email, String newFirstName) {
        boolean updated = userRepository.updateUser(email, newFirstName);
        if (updated) {
            System.out.println("Profil güncellendi: " + email);
        } else {
            System.out.println("Profil güncellenirken hata oluştu.");
        }
    }

    public void logout() {
        System.out.println("Çıkış başarılı.");
    }
}
