package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Persistable;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * This class is responsible for removing some of the
 * boilerplate code when working with JPA's {@link javax.persistence.NamedQuery}
 * <p/>
 * <b>
 * When using vararg methods, take notice of types required.
 * </b>
 */
public abstract class AbstractFacade<T extends Persistable> implements AbstractFacadeInt<T> {

    protected AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private static final Logger LOG = LoggerFactory.getLogger(AbstractFacade.class);
    private Class<T> entityClass;

    @PersistenceContext(name = "pg_BC")
    protected EntityManager entityManager;


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public abstract List<T> getAll();

    public T getById(Long id) {
        LOG.debug("Query database for entity {} with id {}", entityClass.getSimpleName(), id);
        return entityManager.find(entityClass, id);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    @SafeVarargs
    protected final <T> List<T> executeNamedQuery(String queryName, Pair<String, ?>... args) {
        LOG.debug("Executing query {}", queryName);
        TypedQuery<T> query = (TypedQuery<T>) getTypedNamedQuery(queryName);
        return executeQuery(query, args);
    }


    @SafeVarargs
    protected final <T> List<T> executeNamedQuery(String queryName,
                                                  Optional<Integer> firstResult, Optional<Integer> maxResults,
                                                  Pair<String, ?>... args) {
        LOG.debug("Executing query {}", queryName);
        TypedQuery<T> query = (TypedQuery<T>) getTypedNamedQuery(queryName);
        return executeQuery(query, firstResult, maxResults, args);
    }

    @SafeVarargs
    protected final <T> List<T> executeQuery(TypedQuery<T> query, Pair<String, ?>... args) {
        for (Pair<String, ?> arg : args) {
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        return query.getResultList();
    }

    @SafeVarargs
    protected final <T> List<T> executeQuery(TypedQuery<T> query,
                                             Optional<Integer> firstResult, Optional<Integer> maxResults,
                                             Pair<String, ?>... args) {
        for (Pair<String, ?> arg : args) {
            query = query.setParameter(arg.getKey(), arg.getValue());
        }
        query.setFirstResult(firstResult.or(0));
        query.setMaxResults(maxResults.or(10));
        return query.getResultList();
    }


    @SafeVarargs
    protected final <T> List<T> executeQuery(CriteriaQuery<T> criteriaQuery,
                                             Optional<Integer> firstResult, Optional<Integer> maxResults,
                                             Pair<String, ?>... args) {
        return executeQuery(entityManager.createQuery(criteriaQuery), firstResult, maxResults, args);
    }

    protected Predicate getLike(CriteriaBuilder cb, String alias, Path pathRoot) {
        return cb.like(pathRoot.get("firstName"), cb.parameter(String.class, "name"));
    }
    //For f*cks sake, Oracle, please get your *** api together.
    protected Integer executeNamedQueryToCount(String queryName){
        return ((Long) entityManager.createNamedQuery(queryName).getSingleResult()).intValue();
    }

    private TypedQuery<T> getTypedNamedQuery(String queryName) {
        return entityManager.createNamedQuery(queryName, entityClass);
    }
}
