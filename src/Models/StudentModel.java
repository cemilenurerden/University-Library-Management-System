package Models;

public class StudentModel extends UserModel implements Observer{


    public void update(String message){
        System.out.println("bildirim");
    }
    public StudentModel(String email, String password) {
        super(email, password);
    }

    public StudentModel(String email, String password, String LibraryId, String FirstName, String LastName) {
        super(email, password, LibraryId, FirstName, LastName,"student");
    }
    public StudentModel(String email, String password, String LibraryId, String FirstName, String LastName, String role) {
        super(email, password, LibraryId, FirstName, LastName,"student");
    }


    public String borrowBook(String bookID){
        return "d";
    }
    public String rating(int rate){
        return "şl";
    }
    public String returnborrowBook(String bookID){
        return "d";
    }
    public String profile(){
        return "d";
    }
    public String updateprofile(){
        return "d";
    }
}
