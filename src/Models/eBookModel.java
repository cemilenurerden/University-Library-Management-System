package Models;

import java.util.Date;

public class eBookModel extends BookModel{

    public eBookModel(String title, String author, String genre_name, String description, Date publication_year,  String file_url, String status) {
        super(title, author, genre_name, description, publication_year, "e-Book", file_url, status);
    }
}
