package Models;

public class BorrowedState implements IBookState{
    @Override
    public void handleState(BookModel book) {
        book.setState(new BorrowedState());
        System.out.println("alınamaz");
    }
}
