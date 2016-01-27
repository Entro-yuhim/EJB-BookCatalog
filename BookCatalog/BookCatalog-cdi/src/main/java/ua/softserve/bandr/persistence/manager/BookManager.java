package ua.softserve.bandr.persistence.manager;

import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.BookFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.BookHome;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class BookManager extends AbstractManager<Book> {
    @Inject
    private BookHome bookHome;
    @Inject
    private BookFacade bookFacade;

    @Override
    protected AbstractHome<Book> getHome() {
        return bookHome;
    }

    @Override
    protected AbstractFacadeInt<Book> getFacade() {
        return bookFacade;
    }

    public void update(Book book) {
        bookHome.update(book);
    }

    public void delete(Book book) {
        bookHome.delete(book);
    }

    public List<BookRatingDTO> getBookRatingData() {
        List<BookRatingDTO> list = bookFacade.getBookCountByRating();
        return list;
    }
}
