package Models;

public class LostState implements IBookState {
    public void handleState(BookModel book) {
        // Logic for lost book
        book.status = "Lost";
        System.out.println("The book has been marked as lost.");
    }
}
