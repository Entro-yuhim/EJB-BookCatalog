package ua.softserve.bandr.entity;

import com.google.common.base.Objects;
import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
		@NamedQuery(name = Author.GET_ALL,
				query = "SELECT a FROM Author a"),
		@NamedQuery(name = Author.GET_BY_NAME,
				query = "SELECT a FROM Author a WHERE lower(a.lastName) like lower(:namePlaceholder) " +
						"OR lower(a.firstName) like lower(:namePlaceholder) " +
						"OR lower(CONCAT(a.firstName,' ', a.lastName)) like  lower(:namePlaceholder) " +
						"OR lower(CONCAT(a.lastName,' ', a.firstName)) like lower(:namePlaceholder)"),
		@NamedQuery(name = Author.GET_BY_FULL_NAME,//Should i check if reverse data is corret?
				query = "SELECT a FROM Author a WHERE " +
						"LOWER(a.firstName) = LOWER(:firstName) AND " +
						"(LOWER(a.lastName) = LOWER(:lastName) OR a.lastName is null)"),
		@NamedQuery(name = Author.GET_ALL_BY_ID,
						query = "SELECT a FROM Author a " +
								"WHERE a.id IN :idList"),
		@NamedQuery(name = Author.GET_RECORD_COUNT,
				query = "SELECT count(a.id) FROM Author a"),
		@NamedQuery(name = Author.GET_BY_BOOK,
				query = "SELECT a FROM Author a " +
						"JOIN a.books b " +
						"WHERE b.id = :bookId"),
		@NamedQuery(name = Author.DELETE_BY_ID,
				query = "DELETE FROM Author a WHERE a.id = :id"),
		@NamedQuery(name = Author.GET_COUNT_BY_NAME,
				query = "SELECT COUNT(a.id) FROM Author a " +
						"WHERE a.firstName = :firstName AND " +
						"(a.lastName = :lastName OR a.lastName is null)")   // todo case sensitive ?


})
@Table(name = "author",
		uniqueConstraints = @UniqueConstraint(name = "unique_author",
				columnNames = {"first_name", "last_name"}))
public class Author implements Persistable {  // todo variable sorting

	public static final String GET_ALL = "Author.getAll";
	public static final String GET_BY_NAME = "Author.getByName";
	public static final String GET_BY_FULL_NAME = "Author.getByFullName";
	public static final String GET_RECORD_COUNT = "Author.getCount";
	private static final long serialVersionUID = -3297680484923478344L;
	public static final String GET_BY_BOOK = "Author.getByBook";
	public static final String DELETE_BY_ID = "Author.deleteById";
	public static final String GET_COUNT_BY_NAME = "Author.getCountByName";
	public static final String GET_ALL_BY_ID = "Author.getAllById";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
	@SequenceGenerator(name = "author_id_generator", sequenceName = "author_id_seq", allocationSize = 1)
	private Long id;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name")
	private String lastName;

	@Formula("(SELECT round(avg(r.rating)) FROM review r " +
			"JOIN book b ON " +
			"b.id = r.book_id " +
			"JOIN book_author ba ON " +
			"b.id = ba.book_id " +
			"WHERE ba.author_id = id)")
	private Integer rating; // maybe Double ? - something to consider
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "authors")
	private Set<Book> books = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Integer getRating() {
		return rating;
	}

	@Override
	public String getEntityName() {
		return "Author";
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//		Author author = (Author) o;
//		return Objects.equal(firstName, author.firstName) &&
//				Objects.equal(lastName, author.lastName);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hashCode(firstName, lastName);
//	}
}
