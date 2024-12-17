package Controllers;

import Models.UserModel;
import Repositories.UserRepository;

public class UserController {
    private UserRepository userRepository = new UserRepository();

    public String login(String email, String password) {
        String user = userRepository.getUserRoleByEmailandPassword(email, password);
        if (user != null) {
            System.out.println("Giriş başarılı: " );
        } else {
            System.out.println("Giriş başarısız: E-posta veya şifre hatalı.");
        }
        return user;
    }

    public void register(UserModel user, String role) {
        boolean success = userRepository.insertUser(user);
        if (success) {
            System.out.println("Kayıt başarılı: " + user.getFirstName());
        } else {
            System.out.println("Kayıt başarısız.");
        }
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
