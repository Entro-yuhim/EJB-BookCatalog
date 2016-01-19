package ua.softserve.bandr.persistance.facades;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * This class is responsible for removing some of the
 * boilerplate code when working with JPA's {@link javax.persistence.NamedQuery}
 *
 */
//TODO: I can probably do this better. Let's spend some more time later on.
@Stateless
//TODO: think of a better name for class. Srsly.
public class QueryManager {
    //TODO: Probably need to make this into generic.
    public <T> List<T> executeQuery(Query query, Pair<String, String>... args) {
        for (Pair<String, String> arg : args){
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        return query.getResultList();
    }

    public <T> List<T> executeQuery(TypedQuery<T> query,
                                Optional<Integer> firstResult, Optional<Integer> maxResults,
                                Pair<String, String>... args) {
        for (Pair<String, String> arg : args){
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        query.setFirstResult(firstResult.or(0));
        query.setMaxResults(maxResults.or(10));
        return query.getResultList();
    }

    public Predicate getLike(CriteriaBuilder cb, String alias, Path pathRoot) {
        return cb.like(pathRoot.get("firstName"), cb.parameter(String.class, "name"));
    }
}
