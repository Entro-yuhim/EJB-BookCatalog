package ua.softserve.bandr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = Book.GET_ALL,
				query = "SELECT b FROM Book b"),
		@NamedQuery(name = Book.GET_BY_AUTHOR_NAME,
				query = "SELECT b FROM Book b " +
						"JOIN b.authors a " +
						"WHERE a.lastName = :authorNamePlaceholer OR a.firstName = :authorNamePlaceholer"),
		@NamedQuery(name = Book.GET_BY_RATING,
				query = "SELECT b FROM Book b " +
						"WHERE b.rating = :rating"),
		@NamedQuery(name = Book.GET_RECORD_COUNT,
				query = "SELECT count(b.id) FROM Book b"),
		@NamedQuery(name = Book.GET_BY_AUTHOR_ID,
				query = "SELECT b FROM Book b " +
						"JOIN b.authors a " +
						"WHERE a.id = :id"),
		@NamedQuery(name = Book.DELETE_BY_ID,
				query = "DELETE FROM Book b " +
						"WHERE b.id = :id"),
		@NamedQuery(name = Book.GET_BY_NAME_OR_ISBN,
				query = "SELECT b FROM Book b " +
						"WHERE b.iSBN LIKE :isbn OR b.title LIKE :title"),
		@NamedQuery(name = Book.GET_COUNT_BY_ISBN,
				query = "SELECT COUNT (b.id) FROM Book b " +
						"WHERE b.iSBN = :isbn")
})
@NamedNativeQueries({
		@NamedNativeQuery(name = Book.GET_COUNT_BY_RATING,
				query = "SELECT subq.rating AS rating, count(subq.id) AS data_count " +
						"FROM (SELECT b.id, round(avg(r.rating)) rating FROM book b " +
						"JOIN review r ON " +
						"b.id = r.book_id " +
						"GROUP BY b.id) subq " +
						"GROUP BY subq.rating " +
						"ORDER BY subq.rating")
})
public class Book implements Persistable {
	public static final String GET_ALL = "Book.getAll";
	public static final String GET_BY_AUTHOR_NAME = "Book.getByAuthorName";
	public static final String GET_BY_AUTHOR_ID = "Book.getByAuthorId";
	public static final String GET_BY_RATING = "Book.getByRating";
	public static final String GET_COUNT_BY_RATING = "Book.ratingDistribution";
	public static final String GET_COUNT_BY_ISBN = "Book.getCountByISBN";
	public static final String GET_RECORD_COUNT = "Book.getRecordCount";
	private static final long serialVersionUID = -7196067339640930752L;
	public static final String DELETE_BY_ID = "Book.deleteById";
	public static final String GET_BY_NAME_OR_ISBN = "Book.getByNameOrISBN";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
	@SequenceGenerator(name = "book_id_generator", sequenceName = "book_id_seq", allocationSize = 1)
	@JsonIgnore
	private Long id;
	@NotNull
	@Size(min = 1)
	private String title;
	@Column(name = "year_published")
	@Temporal(TemporalType.DATE)
	private Date yearPublished;

	@Column(unique = true, name = "isbn", length = 14)
	@Size(min = 1, max = 14)
	private String iSBN;
	private String publisher;

	@Column(name = "create_date", updatable = false, insertable = false)
	@Temporal(TemporalType.DATE)
	private Date createDate;

	@Formula("(SELECT round(avg(r.rating)) FROM review r WHERE r.book_id = id)")
	private Integer rating;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinTable(name = "book_author",
			joinColumns = @JoinColumn(name = "book_id",
					foreignKey = @ForeignKey(name = "book_author_fk")),
			inverseJoinColumns = @JoinColumn(name = "author_id",
					foreignKey = @ForeignKey(name = "author_book_fk", foreignKeyDefinition = "ON DELETE RESTRICT"))
	)
	@JsonIgnore
	private Set<Author> authors = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id",
			foreignKey = @ForeignKey(name = "review_book_fk"))
	@JsonIgnore
	private Set<Review> reviews = new HashSet<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(Date yearPublished) {
		this.yearPublished = yearPublished;
	}

	public String getiSBN() {
		return iSBN;
	}

	public void setiSBN(String iSBN) {
		this.iSBN = iSBN;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getEntityName() {
		return "Book";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> review) {
		this.reviews = review;
	}

	public Integer getRating() {
		return rating;
	}
}
