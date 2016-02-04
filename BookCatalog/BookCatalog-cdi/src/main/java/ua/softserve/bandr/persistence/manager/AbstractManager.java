package ua.softserve.bandr.persistence.manager;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Persistable;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.utils.LoggingUtils;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 22.01.2016.
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public abstract class AbstractManager<T extends Persistable> {
	@PersistenceContext(name = "pg_BC")
	protected EntityManager entityManager;

	private static final Logger LOG = LoggerFactory.getLogger(AbstractManager.class);

	protected abstract AbstractHome<T> getHome();

	protected abstract AbstractFacadeInt<T> getFacade();

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteBulk(List<T> entities) {
		Validate.notNull(entities, "Received null argument in AbstractManager#deleteBulk");
		LOG.info("Trying to delete entities with id = [{}]", LoggingUtils.getIdCollection(entities));
		for (T entity : entities) {
			delete(entity);
		}
	}

	public List<T> getAll() {
		LOG.info("Attempting to get all entities");
		return getFacade().getAll();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public T getById(Long id) {
		Validate.notNull(id, "Received null argument in AbstractManager#getById");
		LOG.info("Getting entity by id [{}]", id);
		return getFacade().getById(id);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<T> getPaged(Integer startWith, Integer pageSize) {
		Validate.notNull(startWith, "Received null startWith argument in AbstractManager#getPaged");
		Validate.notNull(pageSize, "Received null pageSize argument in AbstractManager#getPaged");
		LOG.info("Fetched entities for StartWith [{}] and PageSize [{}]", startWith, pageSize);
		return getFacade().getPaged(startWith, pageSize);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long persist(@Valid T entity) throws ConstraintCheckException {
		Validate.notNull(entity, "Received null argument in AbstractManager#persist");
		LOG.debug("Trying to persist entity of class [{}]", entity.getEntityName());
		getHome().persist(entity);
		LOG.info("Persisted entity of class [{}] with id [{}]", entity.getEntityName(), entity.getId());
		return entity.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T update(T entity) {
		Validate.notNull(entity, "Received null argument in AbstractManager#update");
		LOG.info("Updating entity of class [{}] and id [{}]", entity.getEntityName(), entity.getId());
		getHome().update(entity);
		return entity;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(T entity) {
		Validate.notNull(entity, "Received null argument in AbstractManager#delete");
		LOG.info("Deleting entity of class [{}] with id [{}]", entity.getEntityName(), entity.getId());
		getHome().delete(entity);
	}

	public Integer getRecordCount(Map<String, String> filter) {
		Validate.notNull(filter, "Received null argument in AbstractManager#getRecordCount");
		return getFacade().getRecordCount(filter);
	}

	public List<T> getPagedFiltered(Integer firstRow, Integer numRows, Map<String, String> filter, Map<String, Boolean> sortingOrder) {
		Validate.notNull(firstRow, "Received null firstRow argument in AbstractManager#getPagedFiltered");
		Validate.notNull(numRows, "Received null numRows argument in AbstractManager#getPagedFiltered");
		Validate.notNull(filter, "Received null filter argument in AbstractManager#getPagedFiltered");
		Validate.notNull(sortingOrder, "Received null sortingOrder argument in AbstractManager#getPagedFiltered");
		return getFacade().getPagedFilteredSorted(firstRow, numRows, filter, sortingOrder);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteBulkById(List<Long> idList) {
		for (Long id : idList) {
			getHome().delete(id);
		}
	}

	;
}
