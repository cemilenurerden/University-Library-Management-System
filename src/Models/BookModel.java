package Models;

import java.util.Date;

public  abstract class BookModel
{
   public String id ;
   public String title;
   public String author;
   public String genre_name;
   public String description;
   public Date publication_year;
   public String category_name;
   public int available_copies;
   public String file_url;
   public String status;

   public BookModel(String title, String author, String genre_name, String description, Date publication_year, String category_name, String file_url, String status) {
      this.title = title;
      this.author = author;
      this.genre_name = genre_name;
      this.description = description;
      this.publication_year = publication_year;
      this.category_name = category_name;
      this.file_url = file_url;
      this.status = status;
   }

}
