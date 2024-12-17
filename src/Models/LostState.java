package Models;

public class LostState implements IBookState{
    @Override
    public void handleState(BookModel book) {
        book.setState(new LostState());
        System.out.println("kayıp alınamaz");
    }
}
