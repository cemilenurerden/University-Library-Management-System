package Repositories;

import Models.BookModel;

public interface IBookRepository {
    String updateBook(BookModel book, String id);
    String insertBook(BookModel book);
    String deleteBook(String id);
    String getBookIdByTitle(String title);
}
