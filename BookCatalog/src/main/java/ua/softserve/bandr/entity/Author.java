package ua.softserve.bandr.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by bandr on 05.01.2016.
 */
@Entity
public class Author {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<Book> booksAuthored;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Book> getBooksAuthored() {
        return booksAuthored;
    }

    public void setBooksAuthored(List<Book> booksAuthored) {
        this.booksAuthored = booksAuthored;
    }
}
