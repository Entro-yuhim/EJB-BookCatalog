package ua.softserve.bandr.persistence.home;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Persistable;

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
		Validate.notNull(entity);
		entityManager.persist(entity);
		LOG.debug("Persisted entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		return entity.getId();
	}

	public T update(T entity) {
		Validate.notNull(entity);
		LOG.debug("Updating entity of class = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		if (entity.getId() == null){
			entityManager.persist(entity);
			return entity;
		}
		return entityManager.merge(entity);
	}

	public void delete(T entity) {
		Validate.notNull(entity);
		LOG.debug("Attempting to remove entity = [{}] with id = [{}]", entity.getEntityName(), entity.getId());
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}
}
