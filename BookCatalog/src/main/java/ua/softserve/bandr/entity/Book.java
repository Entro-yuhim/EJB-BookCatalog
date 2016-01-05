package ua.softserve.bandr.entity;

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
    @Column(columnDefinition = "datetime default NOW()")
    private Date createDate;
    @ManyToMany
    private List<Author> authors;
    @OneToMany
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
}
