package Repositories;

import Models.BookModel;
import Models.UserModel;

import java.util.Date;
import java.util.List;

public interface IBorrowRepository {
    String borrowBook(int userId, int bookId);

    String returnBook(String borrowId);


    List<BookModel> getBorrowedBooksByUser(String userId);


    boolean isBookBorrowed(String bookId);

    List<UserModel> getBorrowersByBook(String bookId);
}