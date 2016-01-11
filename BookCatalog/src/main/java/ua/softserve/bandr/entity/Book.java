package ua.softserve.bandr.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

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
    private int yearPublished;
    @Column(length = 14, unique = true)
    private String iSBN;
    private String publisher;
    @Column(columnDefinition = "timestamp default NOW()")
    private Date createDate;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Author> authors;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="book_id")
    private List<Comment> comments;

//    @Transient
//    private double rating;
//
//    private void setRating(double rating){
//        this.rating = rating;
//    }

    public double getRating() {
        return comments.stream().mapToDouble(Comment::getRating).average().orElse(0);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
}
