package ua.softserve.bandr.persistence.home;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Persistable;
import ua.softserve.bandr.persistence.exceptions.InvalidEntityStateException;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
public abstract class AbstractHome<T extends Persistable> {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractHome.class);

	@PersistenceContext(name = "pg_BC")
	protected EntityManager entityManager;

	public Long persist(T entity) {
		Validate.notNull(entity, "Received null as argument in AbstractHome#persist");
		entityManager.persist(entity);
		LOG.debug("Persisted entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		return entity.getId();
	}

	public T update(T entity) {
		Validate.notNull(entity, "Received null as argument in AbstractHome#update");
		LOG.debug("Updating entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		if (entity.getId() == null) {
			throw new InvalidEntityStateException("Attempted to update entity with null id.");
		}
		return entityManager.merge(entity);
	}
	public abstract void delete(Long id);

	protected void executeDeleteQuery(String queryName, Long id) {
		entityManager.createNamedQuery(queryName).setParameter("id", id).executeUpdate();
	}

	public void delete(T entity) {
		Validate.notNull(entity, "Received null as argument in AbstractHome#delete");
		LOG.debug("Attempting to remove entity = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}
}
