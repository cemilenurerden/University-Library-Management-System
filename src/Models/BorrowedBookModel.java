package Models;

public class BorrowedBookModel {
    private String bookId;
    private String title;
    private String author;
    private String borrowedBy; // Kullanıcı email
    private String borrowDate; // Ödünç alınma tarihi

    public BorrowedBookModel(String bookId, String title, String author, String borrowedBy, String borrowDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.borrowedBy = borrowedBy;
        this.borrowDate = borrowDate;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public String getBorrowDate() {
        return borrowDate;
    }
}
