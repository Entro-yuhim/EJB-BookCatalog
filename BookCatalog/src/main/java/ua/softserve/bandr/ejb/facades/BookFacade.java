package ua.softserve.bandr.ejb.facades;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Stateless
public class BookFacade {
    @Inject
    private QueryManager<Book> bookQueryManager;

    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public List<Book> getAll() {
        return bookQueryManager.executeQuery(entityManager.createNamedQuery("Books.getAll", Book.class));
    }

    public List<Book> getAllByFirstName(String firstName) {
        
        return bookQueryManager.executeQuery(entityManager.createNamedQuery("Books.getByAuthorLastName", Book.class),
                Pair.of("lastName", firstName));
    }

    public List<Book> getPaged(int startWith, int pageSize) {
        return bookQueryManager.executeQuery(entityManager.createNamedQuery("Books.getAll", Book.class), Optional.of(startWith), Optional.of(pageSize));
    }
    public List<Book> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                             Optional<String> filterText){//, Optional<Book.BookSorting> sorting) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        //Join<Book, Author> joinAuthor = book.join("authors");
        Predicate bookName = getPredicate(book.get("title"), "alias", criteriaBuilder);
        //Predicate authorFN = getPredicate(joinAuthor.get("firstName"), "alias", criteriaBuilder);
        //Predicate authorLN = getPredicate(joinAuthor.get("lastName"), "alias", criteriaBuilder);
        CriteriaQuery<Book> finalQuery = criteria.select(book).where(bookName);

        return bookQueryManager.executeQuery(entityManager.createQuery(finalQuery),startWith, pageSize, Pair.of("alias", "%" + filterText.get().toUpperCase() + "%"));
    }

    private Predicate getPredicate(Path fieldParam, String alias, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.upper(fieldParam), criteriaBuilder.parameter(String.class, alias));
    }
}
