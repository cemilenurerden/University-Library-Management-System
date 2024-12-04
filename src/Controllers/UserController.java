package Controllers;

import Models.UserModel;

public class UserController {

    public boolean login() {
        UserModel user = new UserModel("esma", "gk") {
        };
        String role = user.Login();

        switch (role) {
            case "student":
                System.out.println("öğrenci");

                break;
        }

        return false;
    }
}
