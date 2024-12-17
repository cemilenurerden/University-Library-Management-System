package Controllers;

public class LibrarianController {
}
//şimdi farklı bir şey yapıyoruz
//ben 3 yeni sınıf ooluşturdum bunlar controller sınıfı
//user giriş yetkilendirme kayıt çıkış profil bilgileri düzenleme vs gibi işlevleri tutacak
//student ödünç alma verme kitapları inceleme giibi şeyler olucak
//librarian kitap ekleme silme güncelleme ödünç alınan kitapları kimde olduğunu görme gibi işşlevleri yapacak

/*package Controllers;

import Models.BookModel;
import Repositories.BookRepository;
import Repositories.BorrowRepository;

import java.util.List;

public class LibrarianController {
    private BookRepository bookRepository = new BookRepository();
    private BorrowRepository borrowRepository = new BorrowRepository();

    public void addBook(BookModel book) {
        String result = bookRepository.insertBook(book);
        System.out.println(result);
    }

    public void deleteBook(String bookId) {
        String result = bookRepository.deleteBook(bookId);
        System.out.println(result);
    }

    public void updateBook(BookModel book, String bookId) {
        String result = bookRepository.updateBook(book, bookId);
        System.out.println(result);
    }

    public void viewBorrowedBooks() {
        List<String> borrowedBooks = borrowRepository.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("Ödünç alınan kitap bulunmamaktadır.");
        } else {
            System.out.println("Ödünç Alınan Kitaplar:");
            for (String book : borrowedBooks) {
                System.out.println(book);
            }
        }
    }
}
*/