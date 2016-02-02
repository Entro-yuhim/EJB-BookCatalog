package ua.softserve.bandr.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
		@NamedQuery(name = Author.GET_ALL,
				query = "SELECT a FROM Author a"),
		@NamedQuery(name = Author.GET_BY_NAME,
				query = "SELECT a FROM Author a WHERE a.lastName = :lastName"),
		@NamedQuery(name = Author.GET_RECORD_COUNT,
				query = "SELECT count(a.id) FROM Author a")

})
@Table(name = "author",
		uniqueConstraints = @UniqueConstraint(name = "unique_author",
				columnNames = {"first_name", "last_name"}))
public class Author implements Persistable {

	public static final String GET_ALL = "Author.getAll";
	public static final String GET_BY_NAME = "Author.getByLastName";
	public static final String GET_RECORD_COUNT = "Author.getCount";
	private static final long serialVersionUID = -3297680484923478344L;

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
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "authors")
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
}
