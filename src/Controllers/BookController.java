package Controllers;

import Models.BookModel;
import Repositories.BookRepository;

import java.util.List;
import java.util.Map;

public class BookController {
    private BookRepository bookRepository = new BookRepository();

    // Tüm kitapları getir
    public List<BookModel> getAllBooks() {
        List<BookModel> books = bookRepository.getAllBooks();
        if (books != null && !books.isEmpty()) {
            System.out.println("Kitaplar başarıyla alındı.");
        } else {
            System.out.println("Kütüphane boş veya kitaplar alınırken bir hata oluştu.");
        }
        return books;
    }

    public List<BookModel> getBooksByType(int id) {
        List<BookModel> books = bookRepository.getBooksByType(id);
        if (books != null && !books.isEmpty()) {
            System.out.println(" kitaplar başarıyla alındı: " + books.size() + " kitap bulundu.");
        } else {
            System.out.println( "kitap bulunamadı.");
        }
        return books;
    }


    // Yeni kitap ekle
    public String addBook(BookModel book) {
        boolean success = bookRepository.insertBook(book) != null;
        if (success) {
            return "Kitap başarıyla eklendi: " + book.getTitle();
        } else {

            return "Kitap eklenirken bir hata oluştu.";
        }
    }

    // Kitap güncelle
    public void updateBook(String id, BookModel updatedBook) {
        boolean updated = bookRepository.updateBook(updatedBook, id) != null;
        if (updated) {
            System.out.println("Kitap başarıyla güncellendi: ID - " + id);
        } else {
            System.out.println("Kitap güncellenirken bir hata oluştu: ID - " + id);
        }
    }

    // Kitap sil
    public void deleteBook(String id) {
        boolean success = bookRepository.deleteBook(id) != null;
        if (success) {
            System.out.println("Kitap başarıyla silindi: ID - " + id);
        } else {
            System.out.println("Kitap silinirken bir hata oluştu: ID - " + id);
        }
    }
    // Tür listesini getir (genre)
    public Map<String, Integer> getGenres() {
        return bookRepository.getGenres();
    }

    // Kategori listesini getir (category)
    public Map<String, Integer> getCategories() {
        return bookRepository.getCategories();
    }
    // Dinamik arama yap
    public List<BookModel> searchBooks(String query) {
        List<BookModel> books = bookRepository.searchBooks(query);
        if (books != null && !books.isEmpty()) {
            System.out.println("Arama sonuçları başarıyla alındı: " + books.size() + " kitap bulundu.");
        } else {
            System.out.println("Arama sonucu bulunamadı: " + query);
        }
        return books;
    }

    // Çıkış işlemi (isteğe bağlı kontrol için)
    public void logout() {
        System.out.println("Çıkış başarılı.");
    }
}
