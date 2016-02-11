package ua.softserve.bandr.web.controller.book;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.ReviewDTO;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.persistence.manager.ReviewManager;
import ua.softserve.bandr.ws.client.ReviewClient;
import ua.softserve.bandr.ws.client.WebServiceClientException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

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
	@Inject
	private ReviewClient reviewRESTClient;

	private Book book;
	private Set<Author> authors = Sets.newHashSet();
	private List<Review> reviews = Lists.newArrayList();
	private Review review = new Review();

	public void init() {
		book = bookManager.getByIdWithInitializedCollections(bookId);
		this.authors = book.getAuthors();
		this.reviews = Lists.newArrayList(book.getReviews());
	}

	public void save() {
		LOG.info("Saving new comment");
		review.setBook(this.book);
		ReviewDTO reviewData = new ReviewDTO(review);
		try {
			reviewRESTClient.persistReview(reviewData);
		} catch (WebServiceClientException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage());
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

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
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
