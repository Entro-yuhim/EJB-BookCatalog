package ua.softserve.bandr.controller;

import ua.softserve.bandr.ejb.GreeterEJB;
import ua.softserve.bandr.ejb.persistence.BookRepository;
import ua.softserve.bandr.entity.Book;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class Greeter implements Serializable{

    @EJB
    private GreeterEJB greeterEJB;

    @EJB
    private BookRepository repository;

    private String message;

    private Book book;

    @Produces
    @Named("newBook")
    public Book getNewBook(){
        return book;
    }

    public void save(){
        repository.saveBook(book);
        createNewBookContainer();
    }

    @PostConstruct
    private void createNewBookContainer() {
        this.book = new Book();
    }

    public void setName(String name) {
        message = greeterEJB.sayHello(name);
    }

}
