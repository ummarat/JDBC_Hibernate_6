package org.example;

import org.example.entity.Author;
import org.example.entity.Book;
import org.jboss.logging.Logger;
import java.util.List;

public class Main {

    private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());

    public static void main(String[] args) {
        AuthorHelper ah = new AuthorHelper();
        BookHelper helper = new BookHelper();
//
        // додавання авторів
//        Author[] authors = {new Author("Taras", "Shevchenko"),
//                new Author("Vasyl", "Stus"),
//                new Author("Vasyl", "Symonenko")};
//        for (Author author : authors) {
//            ah.addAuthor(author);
//        }

        // виведення книг та авторів цих книг
        List<Book> bookList = helper.getBookList();
        for (Book book : bookList) {
            List<Author> authorList = book.getAuthors();

            System.out.println("\n" + "Book: " + "\n" + book.getName());
            System.out.println("Authors:");
            for (Author author : authorList) {
                System.out.println(author.getName() + " " + author.getLastName());

            }
        }
        //автор по ID
//        Author author = new AuthorHelper().getAuthorById(2);
//        System.out.println(author.getName() + author.getLastName());
    }
}
