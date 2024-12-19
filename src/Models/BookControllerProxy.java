package Models;

import Models.BookModel;
import java.util.List;
import java.util.Map;

public interface BookControllerProxy {
    String addBook(BookModel book);
    List<BookModel> getAllBooks();
    List<BookModel> getBooksByType(int id);
    void deleteBook(String id);
    List<BookModel> searchBooks(String query);
    Map<String, Integer> getGenres();
    Map<String, Integer> getCategories();
}
