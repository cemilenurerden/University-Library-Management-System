package Models;

public class StudentModel extends UserModel{



    public StudentModel(String email, String password) {
        super(email, password);
    }

    public StudentModel(String email, String password, String LibraryId, String FirstName, String LastName) {
        super(email, password, LibraryId, FirstName, LastName,"student");
    }


}
