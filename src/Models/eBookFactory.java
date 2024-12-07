package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class eBookFactory implements BookFactory{

    @Override
    public BookModel createBook(String title, String author, int genre_id, String description, Date publication_year, String file_url, String status) {
        return new eBookModel(title,author,genre_id,description,publication_year,file_url,status);
    }

}
