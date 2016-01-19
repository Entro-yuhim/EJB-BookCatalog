package ua.softserve.bandr.persistance.facades;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.dto.BookRatingDTO;
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
    private QueryManager queryManager;

    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public List<Book> getAll() {
        return queryManager.executeQuery(entityManager.createNamedQuery("Book.getAll", Book.class));
    }

    public List<Book> getAllByFirstName(String firstName) {
        return queryManager.executeQuery(entityManager.createNamedQuery("Book.getByAuthorLastName", Book.class),
                Pair.of("lastName", firstName));
    }

    public List<Book> getBooksByRating(int rating){
        return null;
    }

    public List<Book> getPaged(int startWith, int pageSize) {
        return queryManager.executeQuery(entityManager.createNamedQuery("Book.getAll", Book.class), Optional.of(startWith), Optional.of(pageSize));
    }

    public List<Book> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                             Optional<String> filterText){//, Optional<Book.BookSorting> sorting) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        Join<Book, Author> bookAuthor = book.join("authors");
        CriteriaQuery<Book> finalQuery =
                criteria.select(book)
                        .where(criteriaBuilder
                                .or(queryManager.getLike(criteriaBuilder, "alias", book.get("title")),
                                    queryManager.getLike(criteriaBuilder, "alias", bookAuthor.get("firstName")),
                                    queryManager.getLike(criteriaBuilder, "alias", bookAuthor.get("lastName"))));

        return queryManager.executeQuery(entityManager.createQuery(finalQuery),startWith, pageSize, Pair.of("alias", "%" + filterText.get().toUpperCase() + "%"));
    }

    public List<BookRatingDTO> getBookCountByRating(){
        List<BookRatingDTO> dtos = entityManager.createNamedQuery("Book.getAllByRating").getResultList();
        return dtos;
    }
}
