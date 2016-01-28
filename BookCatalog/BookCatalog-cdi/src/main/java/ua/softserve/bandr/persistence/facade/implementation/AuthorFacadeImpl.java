package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.facade.AuthorFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@LocalBean
public class AuthorFacadeImpl extends AbstractFacade<Author> implements AuthorFacade {

    public AuthorFacadeImpl() {
        super(Author.class);
    }

    public List<Author> getAll() {
        return executeNamedQuery(Author.GET_ALL);
    }

    @Override
    public List<Author> getPaged(Integer startWith, Integer pageSize) {
        return executeNamedQuery(Author.GET_ALL, Optional.of(startWith), Optional.of(pageSize));
    }

    @Override
    public List<Author> getByName(String lastName) {
        return executeNamedQuery(Author.GET_BY_NAME,
                Pair.of("lastName", (Object) lastName), Pair.of("firstName", lastName));
    }

    public List<Author> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                               Optional<String> filterText) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = cb.createQuery(Author.class);
        Root<Author> author = criteriaQuery.from(Author.class);
        criteriaQuery = criteriaQuery.select(author)
                .where(cb.or(getLike(cb, "name", author),
                             getLike(cb, "name", author))).orderBy();
        return executeQuery(criteriaQuery, startWith, pageSize, Pair.of("name", filterText.get()));
    }
}
