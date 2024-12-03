package Models;

public class LibrarianModel extends UserModel{
    public LibrarianModel(String email, String password) {
        super(email, password);
    }

    public LibrarianModel(String email, String password, String LibraryId, String FirstName, String LastName) {
        super(email, password, LibraryId, FirstName, LastName,"librarian");
    }
}
