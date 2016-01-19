package ua.softserve.bandr.persistance.facades;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AuthorFacade {

    @Inject
    private QueryManager queryManager;

    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public List<Author> getAll(){
        return queryManager.executeQuery(entityManager.createNamedQuery("Author.getAll", Author.class));
    }

    public List<Author> getByLastName(String lastName) {
        return queryManager.executeQuery(entityManager.createNamedQuery("Author.getByLastName", Author.class),
                Pair.of("lastName", lastName), Pair.of("firstName", lastName));
    }

    public List<Author> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                               Optional<String> filterText, Optional<List<Author.AuthorSorting>> sorting){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = cb.createQuery(Author.class);
        Root<Author> author = criteriaQuery.from(Author.class);
        criteriaQuery = criteriaQuery.select(author)
                                .where(cb.or(queryManager.getLike(cb, "name", author),
                                        queryManager.getLike(cb, "name", author))).orderBy();
        List<Order> order = createOrder(sorting.get(), cb, author);
        return queryManager.executeQuery(entityManager.createQuery(criteriaQuery), Pair.of("name", filterText.get()));
    }

    private List<Order> createOrder(List<Author.AuthorSorting> sorting, CriteriaBuilder cb, Root root) {
        List <Order> ee = new ArrayList<>();
        for (Author.AuthorSorting sort : sorting){
            switch (sort){
                case FIRST_NAME: ee.add(cb.asc(root.get("firstName")));
                                break;
                case LAST_NAME: ee.add(cb.asc(root.get("lastName")));
                                break;
            }

        }
        return null;
    }

    public List<BookRatingDTO> getBooksByRating(Integer id){
        List<BookRatingDTO> dtoList = new ArrayList<>();
        for (Object[] o : (List<Object[]>) entityManager.createNamedQuery("Author.getAllBooksByRating").getResultList()){
            dtoList.add(new BookRatingDTO(((BigDecimal) o[0]).intValue(), ((BigDecimal) o[1]).longValue()));
        }

        return dtoList;
    }
}
