package Models;

import java.util.Date;

public  abstract class BookModel
{
   public String id ;
   public String title;
   public String author;
   public int genre_id;
   public String description;
   public Date publication_year;
   public int category_id;
   public int available_copies;
   public String file_url;
   public String status;

   public BookModel(String title, String author, int genre_id, String description, Date publication_year, int category_id, String file_url, String status) {
      this.title = title;
      this.author = author;
      this.genre_id = genre_id;
      this.description = description;
      this.publication_year = publication_year;
      this.category_id =category_id;
      this.file_url = file_url;
      this.status = status;
   }

}
