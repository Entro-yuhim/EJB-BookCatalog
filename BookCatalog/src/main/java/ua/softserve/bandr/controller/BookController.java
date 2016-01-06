package ua.softserve.bandr.controller;

import ua.softserve.bandr.ejb.persistence.BookRepository;
import ua.softserve.bandr.entity.Book;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 * Created by bandr on 06.01.2016.
 */
@ManagedBean(name = "bookController")
@RequestScoped
public class BookController {

    @Inject
    private BookRepository repo;

    public BookController() {
        book = new Book();
        System.out.println("Building BookController");
    }

    public Book getBook() {
        return book;
    }

    public String save(){
        System.out.println("Got a book:");
        System.out.println(book);
        repo.saveBook(book);
        return "bookSubmit";
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;

}
