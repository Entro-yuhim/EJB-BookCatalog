package ua.softserve.bandr.entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by bandr on 05.01.2016.
 */
@Entity
public class Book {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int yearPublished;
    @Column(unique = true)
    private String iSBN;
    private String publisher;
    @Column(columnDefinition = "timestamp default NOW()")
    private Date createDate;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Author> authors;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="book_id")
    private List<Comment> comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return this.name + " " + this.iSBN + " " + this.yearPublished;
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
