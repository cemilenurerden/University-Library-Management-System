package Controllers;

public class StudentController {
}

//package Controllers;
//
//import Models.BookModel;
//import Models.UserModel;
//import Repositories.BorrowRepository;
//import Repositories.BookRepository;
//
//import java.util.Date;
//import java.util.List;
//
//public class StudentController {
//    private BorrowRepository borrowRepository = new BorrowRepository();
//    private BookRepository bookRepository = new BookRepository();
//
//    public void borrowBook(UserModel user, String bookId, Date returnDate) {
//        String result = borrowRepository.borrowBook(user.getEmail(), bookId, new Date(), returnDate);
//        System.out.println(result);
//    }
//
//    public void returnBook(String borrowId) {
//        String result = borrowRepository.returnBook(borrowId);
//        System.out.println(result);
//    }
//
//    public void viewBooks() {
//        List<BookModel> books = bookRepository.getAllBooks();
//        if (books.isEmpty()) {
//            System.out.println("Henüz kitap bulunmamaktadır.");
//        } else {
//            for (BookModel book : books) {
//                System.out.println("Kitap: " + book.title + ", Yazar: " + book.author);
//            }
//        }
//    }
//}
