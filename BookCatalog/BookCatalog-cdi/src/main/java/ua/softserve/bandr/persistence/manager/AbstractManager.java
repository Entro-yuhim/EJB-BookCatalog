package ua.softserve.bandr.persistence.manager;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Persistable;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.utils.EntityCollectionTransformer;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
public abstract class AbstractManager<T extends Persistable> {
    @PersistenceContext(name = "pg_BC")
    protected EntityManager entityManager;

    private static final Logger LOG = LoggerFactory.getLogger(AbstractManager.class);

    protected abstract AbstractHome<T> getHome();

    protected abstract AbstractFacadeInt<T> getFacade();

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    protected void deleteBulk(List<T> entities) {
        Validate.notNull(entities);
        LOG.info("Trying to delete entities with id = [{}]", EntityCollectionTransformer.getIdCollection(entities));
        for (T entity : entities) {
            delete(entity);
        }
    }

    public List<T> getAll() {
        LOG.info("Attempting to get all entities of class [{}]");
        return getFacade().getAll();
    }

    public T getById(Long id) {
        Validate.notNull(id);
        LOG.info("Getting entity by id [{}]");
        T entity = getFacade().getById(id);
        return entity;
    }

    public List<T> getPaged(Integer startWith, Integer pageSize) {
        return getFacade().getPaged(startWith, pageSize);
    }

    public void persist(T entity) {
        LOG.debug("Trying to persist entity of class [{}]", entity.getEntityName());
        getHome().persist(entity);
        LOG.info("Persisted entity of class [{}] with id [{}]", entity.getEntityName(), entity.getId());
    }

    public void update(T entity) {
        Validate.notNull(entity);
        LOG.info("Updating entity of class [{}] and id [{}]", entity.getEntityName(), entity.getId());
        getHome().update(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(T entity) {
        Validate.notNull(entity);
        LOG.info("Deleting entity of class [{}] with id [{}]", entity.getEntityName(), entity.getId());
        getHome().delete(entity);
    }

    public int getRecordCount() {
        return getFacade().getRecordCount();
    }
}
