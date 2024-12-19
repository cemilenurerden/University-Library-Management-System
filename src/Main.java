import Models.StudentModel;
import Models.UserModel;
import Repositories.UserRepository;
import Controllers.UserController;
import Views.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreenView() .setVisible(true));
    }
}
