package ua.softserve.bandr.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by bandr on 05.01.2016.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "Books.getAll",
                query = "SELECT b FROM Book b")
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name = "book_id_generator", sequenceName = "book_id_seq", allocationSize = 1)
    private long id;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date yearPublished;

    @Column(length = 14, unique = true)
    private String iSBN;

    private String publisher;
    @Column(columnDefinition = "timestamp default CURRENT_DATE", updatable = false, insertable = false)
    private Date createDate;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "books")
    private List<Author> authors;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="book_id")
    private List<Review> reviews;

//    @Transient
//    private double rating;
//
//    private void setRating(double rating){
//        this.rating = rating;
//    }

    public double getRating() {
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0);
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
    public String toString(){
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
