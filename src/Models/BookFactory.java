package Models;

import java.util.Date;

public interface BookFactory {
    BookModel createBook(String title, String author, int genre_id, String description, Date publication_year, String file_url, String status); // güncelleme ekleme yapılacak kitab için nesnesi oluşturulur.

}
