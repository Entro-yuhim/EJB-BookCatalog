package ua.softserve.bandr.ejb.facades;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.cfg.NotYetImplementedException;
import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class BookFacade {
    @Inject
    private QueryManager<Book> bookQueryManager;
    public List<Book> getAll() {
        return bookQueryManager.executeQuery("Books.getAll", Book.class);
    }

    public List<Book> getAllByFirstName(String firstName) {
        return bookQueryManager.executeQuery("Books.getByAuthorLastName", Book.class,
                Pair.of("lastName", firstName));
    }

    public List<Book> getPaged(int startWith, int pageSize) {
        return bookQueryManager.executeQuery("Books.getAll", Book.class, Optional.of(startWith), Optional.of(pageSize));
    }
    //TODO: discuss this API
    public List<Book> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                             Optional<String> filterText, Optional<Book.BookSorting> sorting) {
        //TODO: maybe using criteria for this is better?
        throw new NotYetImplementedException();
    }
}
