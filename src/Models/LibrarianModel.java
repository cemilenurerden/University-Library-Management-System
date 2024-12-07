package Models;

public class LibrarianModel extends UserModel{
    public LibrarianModel(String email, String password) {
        super(email, password);
    }

    public LibrarianModel(String email, String password, String LibraryId, String FirstName, String LastName) {
        super(email, password, LibraryId, FirstName, LastName,"librarian");
    }

    public String addBook(String bookID){
        return "d";
    }
    public String updateBook(int rate){
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
