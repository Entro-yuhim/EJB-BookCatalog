package ua.softserve.bandr.entity;

import com.google.common.base.Objects;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

/**
 * Created by bandr on 05.01.2016.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_generator")
    @SequenceGenerator(name = "comment_id_generator", sequenceName = "comment_id_seq", allocationSize = 1)
    private long id;
    private String username;
    private int rating;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        Comment comment = (Comment) o;
        return id == comment.id &&
                rating == comment.rating &&
                Objects.equal(username, comment.username) &&
                Objects.equal(message, comment.message) &&
                Objects.equal(book, comment.book);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, rating, message, book);
    }
}
