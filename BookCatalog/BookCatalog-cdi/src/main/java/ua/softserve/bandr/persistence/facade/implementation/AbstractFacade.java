package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * This class is responsible for removing some of the
 * boilerplate code when working with JPA's {@link javax.persistence.NamedQuery}
 *
 */
public abstract class AbstractFacade<T> {

    @PersistenceContext(name="pg_BC")
    protected EntityManager entityManager;
    Class<T> aClass;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public abstract List<T> getAll();

    public T getById(Long id){
        return entityManager.find(getGenericClass(), id);
    }

    @SafeVarargs
    protected final <T> List<T> executeNamedQuery(String queryName, Pair<String, ?>... args){
        TypedQuery<T> query = (TypedQuery<T>) getTypedNamedQuery(queryName);
        return executeQuery(query, args);
    }

    protected <T> List<T> executeNamedQuery(String queryName,
                                                  Optional<Integer> firstResult, Optional<Integer> maxResults,
                                                  Pair<String, ?>... args) {
        TypedQuery<T> query = (TypedQuery<T>) getTypedNamedQuery(queryName);
        return executeQuery(query, firstResult, maxResults, args);
    }

    @SafeVarargs
    protected final <T> List<T> executeQuery(TypedQuery<T> query, Pair<String, ?>... args) {
        for (Pair<String, ?> arg : args){
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        return query.getResultList();
    }

    @SafeVarargs
    protected final <T> List<T> executeQuery(TypedQuery<T> query,
                                             Optional<Integer> firstResult, Optional<Integer> maxResults,
                                             Pair<String, ?>... args) {
        for (Pair<String, ?> arg : args){
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        query.setFirstResult(firstResult.or(0));
        query.setMaxResults(maxResults.or(10));
        return query.getResultList();
    }

    protected Predicate getLike(CriteriaBuilder cb, String alias, Path pathRoot) {
        return cb.like(pathRoot.get("firstName"), cb.parameter(String.class, "name"));
    }


    private Class<T> getGenericClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private TypedQuery<T> getTypedNamedQuery(String queryName){
        return entityManager.createNamedQuery(queryName, (Class<T>) getGenericClass());
    }
}
