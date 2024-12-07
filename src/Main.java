import Models.*;
import Repositories.*;

import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final IBookRepository bookRepository = new BookRepository();
    public static void main(String[] args) {

//        StudentModel student = new StudentModel("esma","gk","1225","esma@gmail","123545")  ;
//        student.Register();

        // bu şekilde kontroller içinde kullan

        BookFactory eBookFactory = new eBookFactory();
        BookModel ebook = eBookFactory.createBook("deneme","deneme",1,"mkckkd",new Date(),"lkdfk","On shelf");
        String add = bookRepository.insertBook(ebook);

            System.out.println(add);


    }
}