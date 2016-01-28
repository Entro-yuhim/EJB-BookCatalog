package ua.softserve.bandr.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;


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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(name = "author_id_generator", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Formula("(SELECT round(avg(r.rating)) FROM review r " +
            "WHERE r.book_id IN (SELECT b.id FROM book b " +
            "JOIN book_author ba ON  " +
            "b.id = ba.book_id " +
            "WHERE ba.author_id = id))")
    private int rating;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

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

    public int getRating() {
        return rating;
    }

    @Override
    public String getEntityName() {
        return "Author";
    }
}
