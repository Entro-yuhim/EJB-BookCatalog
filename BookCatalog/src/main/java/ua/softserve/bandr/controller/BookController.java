package ua.softserve.bandr.controller;

import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.AuthorFacade;
import ua.softserve.bandr.persistence.facade.BookFacade;
import ua.softserve.bandr.persistence.facade.ReviewFacade;
import ua.softserve.bandr.persistence.home.ReviewHome;
import ua.softserve.bandr.persistence.manager.ReviewManager;

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
    @Inject
    private ReviewManager reviewManager;
    @Inject
    private ReviewHome reviewHome;

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
        //List<BookRatingDTO> books2 = authorFacade.getBooksByRating(27);
        Review review = reviewFacade.getById(1L);
        reviewHome.removeReview(review);
        review.setReviewText("HEY I UPDATED TEXT BOYZ111111");
        reviewHome.updateReview(review);
        reviewHome.removeReview(review);
        List<Review> reviews = reviewFacade.getAll();
        reviewManager.deleteBulk(reviews);
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
