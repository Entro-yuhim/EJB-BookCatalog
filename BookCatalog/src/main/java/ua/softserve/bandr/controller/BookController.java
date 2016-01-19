package ua.softserve.bandr.controller;

import com.google.common.base.Optional;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistance.facades.AuthorFacade;
import ua.softserve.bandr.persistance.facades.BookFacade;
import ua.softserve.bandr.persistance.facades.ReviewFacade;
import ua.softserve.bandr.entity.Book;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

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
        List<BookRatingDTO> books2 = authorFacade.getBooksByRating(27);
//        List<Book> testBooks = bookFacade.getAll();
//        List<Book> someBooks = bookFacade.getPagedFilteredSorted(Optional.of(0), Optional.of(10), Optional.of(book.getTitle()));
//        List<Author> testAuthors = authorFacade.getAll();
//        testAuthors = authorFacade.getByLastName("Scott");
//
//        List<Review> testReviews = reviewFacade.getAll();

        return "bookSubmit";
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;

}
