package Models;

import java.util.Date;

public  abstract class BookModel
{
   private String id ;
   public String title;
   public String author;
   public int genre_id;
   public String description;
   public Date publication_year;
   public int category_id;
   public int available_copies;
   public String file_url;
   public String status;
   private IBookState state;


   public BookModel(String title, String author, int genre_id, String description, Date publication_year, int category_id, String file_url, String status) {
      this.title = title;
      this.author = author;
      this.genre_id = genre_id;
      this.description = description;
      this.publication_year = publication_year;
      this.category_id =category_id;
      this.file_url = file_url;
      this.status = status;
      setStateByStatus(status);
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }
   public void setStateByStatus(String status) {
      if("Available".equalsIgnoreCase(status)){
         this.state = new AvailableState();

      }
      else if("Borrowed".equalsIgnoreCase(status)){
         this.state = new BorrowedState();

      }
      else {
         this.state = new LostState();
      }
   }
   public IBookState getState() {
      return state;
   }

   public void setState(IBookState state) {
      this.state = state;
      state.handleState(this);
   }

   public void performStateAction() {
      state.handleState(this);
   }
}

