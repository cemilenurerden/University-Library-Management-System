package Models;

import java.util.Date;

public class PrintedBookModel extends BookModel{
    public PrintedBookModel(String title, String author, String genre_name, String description, Date publication_year,  String file_url, String status) {
        super(title, author, genre_name, description, publication_year, "printed-book", file_url, status);
    }
}
