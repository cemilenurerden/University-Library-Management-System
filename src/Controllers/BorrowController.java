package Controllers;

import Models.BookModel;
import Models.BorrowedBookModel;
import Repositories.BorrowRepository;
import Repositories.UserRepository;

import java.util.List;

public class BorrowController {
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;

    public BorrowController() {
        this.userRepository = new UserRepository();
        this.borrowRepository = new BorrowRepository();
    }
    public List<BorrowedBookModel> getAllBorrowedBooks() {
        return borrowRepository.getAllBorrowedBooks();
    }
    public String borrowBook(String email, int bookId){
        String id = userRepository.getUserIdByEmail(email);
        return borrowRepository.borrowBook(Integer.parseInt(id), bookId);
    }
    public List<BookModel> getUserBorrowedBooks(String email) {
        String id = userRepository.getUserIdByEmail(email);
        return borrowRepository.getBorrowedBooksByUser(id);
    }

    public String returnBorrowedBook(String borrowId) {
        return borrowRepository.returnBook(borrowId);
    }
}
