package ua.softserve.bandr.web.controller.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.persistence.manager.ConstraintCheckException;
import ua.softserve.bandr.persistence.manager.ReviewManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by bandr on 04.02.2016.
 */
@ManagedBean
@ViewScoped
public class BookViewController {
	private static final Logger LOG = LoggerFactory.getLogger(BookViewController.class);
	private Long bookId;
	@Inject
	private BookManager bookManager;
	@Inject
	private AuthorManager authorManager;
	@Inject
	private ReviewManager reviewManager;

	private Book book;
	private List<Author> authors;
	private List<Review> reviews;
	private Review review = new Review();

	public void init() {
		book = bookManager.getById(bookId);
		this.authors = authorManager.getByBookId(bookId);
		this.reviews = reviewManager.getByBookId(bookId);
	}

	public void save() {
		LOG.info("Saving new comment");
		review.setBook(this.book);
		try {
			//TODO: think about it a bit more later.
			reviewManager.persist(review);
		} catch (ConstraintCheckException e) {
			LOG.info("Error when persisting review", e);
		}
		this.reviews = reviewManager.getByBookId(bookId);
		review = new Review();
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
}
