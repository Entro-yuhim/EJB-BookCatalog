package ua.softserve.bandr.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "Book.getAll",
                query = "SELECT b FROM Book b"),
        @NamedQuery(name = "Book.getByAuthorLastName",
                query = "SELECT b FROM Book b " +
                        "JOIN b.authors a " +
                        "WHERE a.lastName = :lastName"),
        @NamedQuery(name = "Book.getById",
                query = "SELECT b FROM Book b " +
                        "WHERE b.id=:id"),
        @NamedQuery(name = "Book.getByRating",
                query = "SELECT b FROM Book b " +
                        "WHERE b.rating > :rating")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name = "book_id_generator", sequenceName = "book_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    @Column(name = "year_published")
    @Temporal(TemporalType.DATE)
    private Date yearPublished;

    @Column(unique = true, name = "isbn")
    private String iSBN;
    private String publisher;

    @Column(name = "create_date", updatable = false, insertable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Formula("(SELECT avg(r.rating) FROM review r WHERE r.book_id = id)")
    private Integer rating;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            //TODO: not crucial, but doesn't work. Need to figure out why.
            joinColumns = @JoinColumn(name = "book_id",
                    foreignKey = @ForeignKey(name = "book_author_fk")),
            inverseJoinColumns = @JoinColumn(name = "author_id",
                    foreignKey = @ForeignKey(name = "author_book_fk", foreignKeyDefinition = "ON DELETE No Action"))
    )
    private Set<Author> authors = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id",
            foreignKey = @ForeignKey(name = "review_book_fk"))
    private Set<Review> reviews = new HashSet<>();

    public enum BookSorting{
        ID, TITLE, PUBLISHED_DATE, PUBLISHER, RATING, AUTHOR
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
