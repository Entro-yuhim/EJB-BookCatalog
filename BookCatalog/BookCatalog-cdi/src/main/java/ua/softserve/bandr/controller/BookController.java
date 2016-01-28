package ua.softserve.bandr.controller;

import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.facade.AuthorFacade;
import ua.softserve.bandr.persistence.facade.BookFacade;
import ua.softserve.bandr.persistence.home.AuthorHome;
import ua.softserve.bandr.persistence.home.BookHome;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

@ManagedBean(name = "bookController")
@RequestScoped
public class BookController {

    @Inject
    private BookFacade bookFacade;
    @Inject
    private BookHome bookHome;
    @Inject
    private AuthorFacade authorFacade;
    @Inject
    private AuthorHome authorHome;

    public BookController() {
        book = new Book();
        System.out.println("Building BookController");
    }

    public Book getBook() {
        return book;
    }

    public String save(){
        Author a = authorFacade.getById(1L);

        return "bookSubmit";
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;

}
