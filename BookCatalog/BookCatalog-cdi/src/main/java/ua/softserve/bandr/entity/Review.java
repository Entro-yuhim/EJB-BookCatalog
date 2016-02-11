package ua.softserve.bandr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Check(constraints = "rating BETWEEN 1 AND 5")
@NamedQueries({
		@NamedQuery(name = Review.GET_ALL, query = "SELECT r FROM Review r"),
		@NamedQuery(name = Review.GET_BY_BOOK, query = "SELECT r FROM Review r WHERE r.book.id = :id"),
		@NamedQuery(name = Review.GET_RECORD_COUNT, query = "SELECT count(r.id) FROM Review r"),
		@NamedQuery(name = Review.DELETE_BY_ID, query = "DELETE FROM Review r WHERE r.id = :id")

})
public class Review implements Persistable {
	public static final String GET_ALL = "Reviews.getAll";
	public static final String GET_BY_BOOK = "Reviews.getByBook";
	public static final String GET_RECORD_COUNT = "Reviews.getRecordCount";
	private static final long serialVersionUID = 4070328724532468249L;
	public static final String DELETE_BY_ID = "Reviews.deleteById";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_generator")
	@SequenceGenerator(name = "review_id_generator", sequenceName = "review_id_seq", allocationSize = 1)
	private Long id;

	@Column(length = 255)
	private String username;

	@Column(length = 1, nullable = false)
	@Min(value = 1, message = "Rating should be between 1 and 5.")
	@Max(value = 5, message = "Rating should be between 1 and 5.")
	private Integer rating;

	@Lob
	@Column(name = "review_text", nullable = false)
	@NotNull(message = "Text of review cannot be null.")
	private String reviewText;

	@ManyToOne
	private Book book;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String message) {
		this.reviewText = message;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String getEntityName() {
		return "Review";
	}
}
