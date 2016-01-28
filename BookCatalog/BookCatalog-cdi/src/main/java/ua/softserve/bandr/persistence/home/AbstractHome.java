package ua.softserve.bandr.persistence.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Persistable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractHome<T extends Persistable> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractHome.class);

    @PersistenceContext(name = "pg_BC")
    protected EntityManager entityManager;

    public void persist(T entity) {
        entityManager.persist(entity);
        LOG.debug("Persisted entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public T update(T entity) {
        LOG.debug("Updating entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
        return entityManager.merge(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(T entity) {
        LOG.debug("Attempting to remove entity = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
