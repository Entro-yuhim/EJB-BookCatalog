package ua.softserve.bandr.entity;

import com.google.common.base.Objects;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Created by bandr on 05.01.2016.
 */
@Entity
@Check(constraints = "rating BETWEEN 1 AND 5")
@NamedQueries({
        @NamedQuery(name="Reviews.getAll", query="SELECT r FROM Review r"),
        @NamedQuery(name="Reviews.getById", query="SELECT r FROM Review r WHERE r.id = :id")
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_generator")
    @SequenceGenerator(name = "review_id_generator", sequenceName = "review_id_seq", allocationSize = 1)
    private long id;
    @Column(length = 15)
    private String username;
    @Column(length=1, nullable = false)

    private int rating;
    @Column(name="review_text", nullable = false)
    private String reviewText;
    @ManyToOne
    private Book book;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String message) {
        this.reviewText = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                rating == review.rating &&
                Objects.equal(username, review.username) &&
                Objects.equal(reviewText, review.reviewText) &&
                Objects.equal(book, review.book);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, rating, reviewText, book);
    }
}
