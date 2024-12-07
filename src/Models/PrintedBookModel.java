package Models;

import java.util.Date;

public class PrintedBookModel extends BookModel{
    public PrintedBookModel(String title, String author, int genre_name, String description, Date publication_year,  String file_url, String status) {
        super(title, author, genre_name, description, publication_year, 2, file_url, status);
    }
}
