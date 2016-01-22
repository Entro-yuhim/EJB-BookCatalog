package ua.softserve.bandr.persistence.facade.Impl;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.facade.BookFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@LocalBean
public class BookFacadeImpl extends AbstractFacade<Book> implements BookFacade {

    public List<Book> getAll() {
        return executeNamedQuery(Book.GET_ALL);
    }

    @Override
    public List<Book> getPaged(Integer startWith, Integer pageSize) {
        return executeNamedQuery(Book.GET_ALL, Optional.of(startWith), Optional.of(pageSize));
    }

    public List<Book> getAllByAuthor(String authorFilter) {
        return executeNamedQuery(Book.GET_BY_AUTHOR_NAME,
                Pair.of("name", authorFilter));
    }

    public List<Book> getBooksByRating(int rating) {
        return executeNamedQuery(Book.GET_BY_RATING, Pair.of("rating", rating));
    }

    //I need to see what type of data will I receive from JSF/RF for pagination to properly implement this
    public List<Book> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                             Optional<String> filterText) {//, Optional<Book.BookSorting> sorting) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        Join<Book, Author> bookAuthor = book.join("authors");
        CriteriaQuery<Book> finalQuery =
                criteria.select(book)
                        .where(criteriaBuilder
                                .or(getLike(criteriaBuilder, "alias", book.get("title")),
                                        getLike(criteriaBuilder, "alias", bookAuthor.get("firstName")),
                                        getLike(criteriaBuilder, "alias", bookAuthor.get("lastName"))));
        return executeQuery(entityManager.createQuery(finalQuery), startWith, pageSize, Pair.of("alias", "%" + filterText.get().toUpperCase() + "%"));
    }

    public List<BookRatingDTO> getBookCountByRating() {
        return entityManager.createNativeQuery("Book.getAllBooksByRating").getResultList();
    }
}