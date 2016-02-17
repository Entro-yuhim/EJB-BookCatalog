package ua.softserve.bandr.persistence.home;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Persistable;
import ua.softserve.bandr.persistence.exceptions.InvalidEntityStateException;
import ua.softserve.bandr.persistence.exceptions.PersistenceException;
import ua.softserve.bandr.utils.ValidateArgument;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
public abstract class AbstractHome<T extends Persistable> {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractHome.class);

	@PersistenceContext(name = "pg_BC")
	protected EntityManager entityManager;

	public T persist(T entity) throws PersistenceException {
		ValidateArgument.notNull(entity, "Received null as argument in AbstractHome#persist");
		try {
			entityManager.persist(entity);// todo: some Exception can be here
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
		LOG.debug("Persisted entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		return entity;
	}

	public T update(T entity) throws PersistenceException {
		ValidateArgument.notNull(entity, "Received null as argument in AbstractHome#update");
		LOG.debug("Updating entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		if (entity.getId() == null) {
			throw new InvalidEntityStateException("Attempted to update entity with null id.");
		}
		try {
			return entityManager.merge(entity);
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	public abstract Long delete(Long id);

	protected void executeDeleteQuery(String queryName, Long id) {
		ValidateArgument.notNull(queryName);
		ValidateArgument.notNull(id);
		entityManager.createNamedQuery(queryName).setParameter("id", id).executeUpdate();
	}

	public Long delete(T entity) throws PersistenceException {
		ValidateArgument.notNull(entity, "Received null as argument in AbstractHome#delete");
		LOG.debug("Attempting to remove entity = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		try {
			entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
		return entity.getId();
	}
}
