package ua.softserve.bandr.controller;

import ua.softserve.bandr.ejb.facades.AuthorFacade;
import ua.softserve.bandr.ejb.facades.BookFacade;
import ua.softserve.bandr.ejb.facades.ReviewFacade;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Review;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by bandr on 06.01.2016.
 */
@ManagedBean(name = "bookController")
@RequestScoped
public class BookController {

    @Inject
    private BookFacade bookFacade;
    @Inject
    private AuthorFacade authorFacade;
    @Inject
    private ReviewFacade reviewFacade;

    public BookController() {
        book = new Book();
        System.out.println("Building BookController");
    }

    public Book getBook() {
        return book;
    }

    public String save(){
        System.out.println("Got a book:");
        System.out.println(book);
        List<Book> testBooks = bookFacade.getAll();
        testBooks = null;
        testBooks = bookFacade.getAllByFirstName("Slade");
        testBooks = bookFacade.getPaged(5, 20);

        List<Author> testAuthors = authorFacade.getAll();
        testAuthors = authorFacade.getByLastName("Scott");

        List<Review> testReviews = reviewFacade.getAll();

        return "bookSubmit";
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;

}
