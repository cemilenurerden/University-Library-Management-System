package Models;

import java.util.Date;

public class VoidBookModel extends BookModel{

    public VoidBookModel(String title, String author, String genre_name, String description, Date publication_year, String file_url, String status) {
        super(title, author, genre_name, description, publication_year, "void-Book", file_url, status);
    }
}
