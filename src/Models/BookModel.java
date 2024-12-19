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

   public BookModel(String id, String title, String author){
      this.id = id ;
      this.title =title;
      this.author = author;

   }

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

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public int getGenre_id() {
      return genre_id;
   }

   public void setGenre_id(int genre_id) {
      this.genre_id = genre_id;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Date getPublication_year() {
      return publication_year;
   }

   public void setPublication_year(Date publication_year) {
      this.publication_year = publication_year;
   }

   public int getCategory_id() {
      return category_id;
   }

   public void setCategory_id(int category_id) {
      this.category_id = category_id;
   }

   public int getAvailable_copies() {
      return available_copies;
   }

   public void setAvailable_copies(int available_copies) {
      this.available_copies = available_copies;
   }

   public String getFile_url() {
      return file_url;
   }

   public void setFile_url(String file_url) {
      this.file_url = file_url;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}

