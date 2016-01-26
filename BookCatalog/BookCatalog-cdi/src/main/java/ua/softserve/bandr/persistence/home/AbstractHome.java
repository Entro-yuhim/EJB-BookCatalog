package ua.softserve.bandr.persistence.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractHome<T> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @PersistenceContext(name = "pg_BC")
    protected EntityManager entityManager;

    public void persist(T entity) {
        entityManager.persist(entity);
        LOG.info("Persisted Entity of class = {} with id = {}", entity.getClass(), entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(T entity) {
        LOG.info("Attempting to remove entity {} with id = {}", entity.getClass(), entity);
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
