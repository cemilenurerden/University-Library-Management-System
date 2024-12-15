package Models;
import java.util.Date;

public class Book {

        public IBookState currentState;
        public String title;
        public String author;
        public int genre_id;
        public String description;
        public Date publication_year;
        public String status;

        public Book(String title, String author, int genre_id, String description, Date publication_year) {
            this.title = title;
            this.author = author;
            this.genre_id = genre_id;
            this.description = description;
            this.publication_year = publication_year;
            this.status = "Available";  // Initially, the book is available
            this.currentState = new AvailableState(); // Set initial state to Available
        }



    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre ID: " + genre_id);
        System.out.println("Description: " + description);
        System.out.println("Publication Year: " + publication_year);
        System.out.println("Status: " + status);
    }
    }

