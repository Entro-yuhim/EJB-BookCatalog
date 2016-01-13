package ua.softserve.bandr.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

/**
 * Created by bandr on 05.01.2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Author.getAll",
                query="SELECT a FROM Author a"),
        @NamedQuery(name = "Author.getByLastName",
                query="SELECT a FROM Author a WHERE a.lastName = :lastName")
        })
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(name = "author_id_generator", sequenceName = "author_id_seq", allocationSize = 1)
    private long id;
    @Column(length = 25, nullable = false)
    private String firstName;
    @Column(length = 40)
    private String lastName;

    public enum AuthorSorting{
        ID, FIRST_NAME, LAST_NAME, RATING
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "authors")
    private List<Book> books;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> booksAuthored) {
        this.books = booksAuthored;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                Objects.equal(firstName, author.firstName) &&
                Objects.equal(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firstName, lastName);
    }
}
