package ua.softserve.bandr.ejb.facades;

import com.google.common.base.Optional;
import org.hibernate.cfg.NotYetImplementedException;
import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BookFacade {
    @PersistenceContext(name = "pg_BC")
    private EntityManager entityManager;

    public List<Book> getAll() {
        return entityManager
                .createNamedQuery("Books.getAll", Book.class)
                .getResultList();
    }

    public List<Book> getAllByFirstName(String firstName) {
        return entityManager
                .createNamedQuery("Books.getByAuthorLastName", Book.class)
                .setParameter("lastName", firstName)
                .getResultList();
    }

    public List<Book> getPaged(int startWith, int pageSize) {
        return entityManager.createNamedQuery("Books.getAll", Book.class)
                .setFirstResult(startWith)
                .setMaxResults(pageSize)
                .getResultList();
    }
    //TODO: discuss this API
    public List<Book> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                             Optional<String> filterText, Optional<Book.BookSorting> sorting) {
        //TODO: maybe using criteria for this is better?
        throw new NotYetImplementedException();
    }
}
