package ua.softserve.bandr.entity;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Check(constraints = "(first_name, last_name) UNIQUE")
@NamedQueries({
        @NamedQuery(name = "Author.getAll",
                query="SELECT a FROM Author a"),
        @NamedQuery(name = "Author.getByLastName",
                query="SELECT a FROM Author a WHERE a.lastName = :lastName"),
        @NamedQuery(name = "doStuff",
                query = "SELECT b.id, avg(r.rating) FROM Book b " +
                        "JOIN b.reviews r " +
                        "group by b.id")
        })
@NamedNativeQueries({
        @NamedNativeQuery(name = "Author.getAllBooksByRating",
                query = "SELECT rating, count(id) " +
                        "FROM (SELECT b.id, round(avg(r.rating)) rating FROM book b " +
                        "JOIN review r ON " +
                        "b.id = r.book_id " +
                        "group by b.id) subq " +
                        "group by rating")
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(name = "author_id_generator", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public enum AuthorSorting{
        FIRST_NAME, LAST_NAME, RATING
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "authors", fetch = FetchType.EAGER)
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
}
