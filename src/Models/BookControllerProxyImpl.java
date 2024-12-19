package Models;

import Controllers.BookController;

import java.util.List;
import java.util.Map;

public class BookControllerProxyImpl implements BookControllerProxy {
    private final BookController bookController;
    private final String role;

    public BookControllerProxyImpl(String role) {
        this.bookController = new BookController();
        this.role = role;
    }

    @Override
    public String addBook(BookModel book) {
        if ("librarian".equalsIgnoreCase(role)) {
            return  bookController.addBook(book);
        } else {
            return  "Yetkisiz işlem: Sadece kütüphaneciler kitap ekleyebilir.";
        }
    }

    @Override
    public List<BookModel> getAllBooks() {
        return bookController.getAllBooks(); // Herkes görüntüleyebilir
    }

    @Override
    public List<BookModel> getBooksByType(int id) {
        return bookController.getBooksByType(id); // Tür bazlı görüntüleme
    }

    @Override
    public void deleteBook(String id) {
        if ("librarian".equalsIgnoreCase(role)) {
            bookController.deleteBook(id);
            System.out.println("Kitap başarıyla silindi.");
        } else {
            System.out.println("Yetkisiz işlem: Sadece kütüphaneciler kitap silebilir.");
        }
    }

    @Override
    public List<BookModel> searchBooks(String query) {
        return bookController.searchBooks(query); // Herkes arama yapabilir
    }
  public   Map<String, Integer> getGenres(){
        return bookController.getGenres();
  }
  public   Map<String, Integer> getCategories(){
        return bookController.getCategories();
  }
}
