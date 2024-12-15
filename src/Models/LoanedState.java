package Models;

public class LoanedState implements IBookState{
    public void handleState(BookModel book) {
        // Logic for loaned book
        book.status = "Loaned";
        System.out.println("The book has been loaned out.");
    }
}
