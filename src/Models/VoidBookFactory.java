package Models;

import java.util.Date;

public class VoidBookFactory implements BookFactory{
    @Override
    public BookModel createBook(String title, String author, int genre_name, String description, Date publication_year, String file_url, String status) {
        return new VoidBookModel(title,author,genre_name,description,publication_year,file_url,status);
    }


}
