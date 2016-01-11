package ua.softserve.bandr.controller;

import ua.softserve.bandr.ejb.persistence.BookRepository;
import ua.softserve.bandr.entity.Book;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by bandr on 11.01.2016.
 */
@ManagedBean(name = "bookFetcher")
@RequestScoped
public class BookFetcher {
    public BookFetcher() {
        System.out.println("I'm alive");
    }

    @Inject
    private BookRepository repository;

    public List<Book> getBooks(){
        return repository.getBooks();
    }
}
