package ua.softserve.bandr.persistence.facade;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class BookFacade {
    @Inject
    private PersistenceManager persistenceManager;

    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public List<Book> getAll() {
        return persistenceManager.executeQuery(entityManager.createNamedQuery("Book.getAll", Book.class));
    }

    public List<Book> getAllByAuthor(String authorFilter) {
        return persistenceManager.executeQuery(entityManager.createNamedQuery("Book.getByAuthorName", Book.class),
                Pair.of("name", authorFilter));
    }

    public List<Book> getBooksByRating(int rating){
        return persistenceManager.executeQuery(entityManager.createNamedQuery("Book.getByRating", Book.class));
    }


    public List<Book> getPaged(int startWith, int pageSize) {
        return persistenceManager.executeQuery(entityManager.createNamedQuery("Book.getAll", Book.class), Optional.of(startWith), Optional.of(pageSize));
    }

    //I need to see what type of data will I receive from JSF/RF for pagination to properly implement this
    public List<Book> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                             Optional<String> filterText){//, Optional<Book.BookSorting> sorting) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        Join<Book, Author> bookAuthor = book.join("authors");
        CriteriaQuery<Book> finalQuery =
                criteria.select(book)
                        .where(criteriaBuilder
                                .or(persistenceManager.getLike(criteriaBuilder, "alias", book.get("title")),
                                    persistenceManager.getLike(criteriaBuilder, "alias", bookAuthor.get("firstName")),
                                    persistenceManager.getLike(criteriaBuilder, "alias", bookAuthor.get("lastName"))));
        return persistenceManager.executeQuery(entityManager.createQuery(finalQuery),startWith, pageSize, Pair.of("alias", "%" + filterText.get().toUpperCase() + "%"));
    }

    public List<BookRatingDTO> getBookCountByRating(){
        return entityManager.createNativeQuery("Book.getAllBooksByRating").getResultList();
    }
}
