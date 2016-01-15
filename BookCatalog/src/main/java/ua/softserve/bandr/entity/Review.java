package ua.softserve.bandr.entity;

import org.hibernate.annotations.Check;

import javax.persistence.*;

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
    private Long id;

    @Column(length = 15)
    private String username;

    @Column(length=1, nullable = false)
    private Integer rating;

    @Lob
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
