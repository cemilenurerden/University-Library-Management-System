package Models;

import java.util.Date;

public class eBookModel extends BookModel{

    public eBookModel(String title, String author, int genre_id, String description, Date publication_year, String file_url, String status) {
        super(title, author, genre_id, description, publication_year, 1, file_url, status);
    }
}
