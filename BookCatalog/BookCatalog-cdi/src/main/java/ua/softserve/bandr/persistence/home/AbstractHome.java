package ua.softserve.bandr.persistence.home;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractHome<T> {

    @PersistenceContext(name = "pg_BC")
    protected EntityManager entityManager;

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
