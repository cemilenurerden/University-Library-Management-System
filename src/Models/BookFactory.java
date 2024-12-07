package Models;

import java.util.Date;

public interface BookFactory {
    BookModel createBook(String title, String author, String genre_name, String description, Date publication_year, String file_url, String status); // güncelleme ekleme yapılacak kitab için nesnesi oluşturulur.
    String updateBook(BookModel book, String id);
    String insertBook(BookModel book);
    String deleteBook(String id);
    String getBookIdByTitle(String title);
}
