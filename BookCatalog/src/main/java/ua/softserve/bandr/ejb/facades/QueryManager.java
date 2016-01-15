package ua.softserve.bandr.ejb.facades;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class is responsible for removing some of the
 * boilerplate code when working with JPA's {@link javax.persistence.NamedQuery}
 *
 * @param <T> target class of the entity.
 */
//TODO: I can probably do this better. Let's spend some more time later on.
@Stateless
//TODO: think of a better name for class. Srsly.
public class QueryManager<T> {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;


    //TODO: Probably need to make this into generic.
    public List<T> executeQuery(String queryName, Class<T> targetEntityClass, Pair<String, String>... args) {
        TypedQuery query = entityManager.createNamedQuery(queryName, targetEntityClass);
        for (Pair<String, String> arg : args){
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        return query.getResultList();
    }

    public List<T> executeQuery(String queryName, Class<T> targetEntityClass,
                                Optional<Integer> firstResult, Optional<Integer> maxResults,
                                Pair<String, String>... args) {
        TypedQuery query = entityManager.createNamedQuery(queryName, targetEntityClass);
        for (Pair<String, String> arg : args){
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        query.setFirstResult(firstResult.or(0));
        query.setMaxResults(maxResults.or(10));
        return query.getResultList();
    }
}
