package Models;

public class AvailableState implements IBookState{
    @Override
    public void handleState(BookModel book) {
        book.setState(new AvailableState());
        System.out.println("alabilirsiniz");
    }
}
