package ua.softserve.bandr.entity;

import com.google.common.base.Objects;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by bandr on 05.01.2016.
 */
@Entity
@NamedQueries(
        {@NamedQuery(name = "Books.getAll",
                query = "SELECT b FROM Book b"),
         @NamedQuery(name= "Books.getByAuthorLastName",
                query="SELECT b FROM Book b " +
                        "JOIN b.authors a " +
                        "WHERE a.lastName = :lastName"),
         @NamedQuery(name="Book.getById",
                 query="SELECT b FROM Book b " +
                         "WHERE b.id=:id")

        })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name = "book_id_generator", sequenceName = "book_id_seq", allocationSize = 1)
    private long id;
    @Column(length = 50)
    private String title;
    @Temporal(TemporalType.DATE)
    private Date yearPublished;

    @Column(length = 14, unique = true)
    private String iSBN;
    @Column(length = 30)
    private String publisher;

    @Column(updatable = false, insertable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_author",
            //TODO: not crucial, but doesn't work. Need to figure out why.
            joinColumns = @JoinColumn(name = "book_id",
                    foreignKey = @ForeignKey(name = "book_author_fk")),
            inverseJoinColumns = @JoinColumn(name = "author_id",
                    foreignKey = @ForeignKey(name = "author_book_fk", foreignKeyDefinition = "ON DELETE No Action"))
    )
    private List<Author> authors;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id",
            foreignKey = @ForeignKey(name = "review_book_fk"))
    private List<Review> reviews;

    public double getRating() {
        //TODO: Change this.
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0);
    }

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

    @Override
    public String toString() {
        return this.title + " " + this.iSBN + " " + this.yearPublished;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Author> getAuthors() {

        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                yearPublished == book.yearPublished &&
                Objects.equal(title, book.title) &&
                Objects.equal(iSBN, book.iSBN) &&
                Objects.equal(publisher, book.publisher) &&
                Objects.equal(createDate, book.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, yearPublished, iSBN, publisher, createDate);
    }

    @OneToMany(mappedBy = "book")
    private Collection<Review> review;

    public Collection<Review> getReview() {
        return review;
    }

    public void setReview(Collection<Review> review) {
        this.review = review;
    }
}
