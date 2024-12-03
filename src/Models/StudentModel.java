package Models;

public class StudentModel extends UserModel{
    public StudentModel(String email, String password) {
        super(email, password);
    }

    public void yaz(){
        System.out.println(getEmail()+ "    " +getPassword());
    }
}
