package Models;

public class AvailableState implements IBookState {


    public void handleState(BookModel book) {
        // Logic for available book
        book.status = "Available";
        System.out.println("The book is available for borrowing.");
    }
}
