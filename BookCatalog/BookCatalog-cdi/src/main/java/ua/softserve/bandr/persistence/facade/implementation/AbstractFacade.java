package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private final Class<T> entityClass;

	@PersistenceContext(name = "pg_BC")
	protected EntityManager entityManager;


	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	//Should never be used in actual environment.
	public abstract List<T> getAll();

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public T getById(Long id) {
		LOG.debug("Query database for entity {} with id {}", entityClass.getSimpleName(), id);
		return entityManager.find(entityClass, id);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	protected CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	@SafeVarargs
	protected final List<T> executeNamedQuery(String queryName, Pair<String, ?>... args) {
		LOG.debug("Executing query {}", queryName);
		TypedQuery<T> query = (TypedQuery<T>) getTypedNamedQuery(queryName);
		return executeQuery(query, args);
	}


	@SafeVarargs
	protected final List<T> executeNamedQuery(String queryName,
											  Optional<Integer> firstResult, Optional<Integer> maxResults,
											  Pair<String, ?>... args) {
		LOG.debug("Executing query {}", queryName);
		TypedQuery<T> query = (TypedQuery<T>) getTypedNamedQuery(queryName);
		return executeQuery(query, firstResult, maxResults, args);
	}

	@SafeVarargs
	protected final List<T> executeQuery(TypedQuery<T> query, Pair<String, ?>... args) {
		TypedQuery<T> query1 = query;
		for (Pair<String, ?> arg : args) {
			query1 = query1.setParameter(arg.getKey(), arg.getValue());
		}
		return query1.getResultList();
	}

	@SafeVarargs
	protected final List<T> executeQuery(TypedQuery<T> query,
										 Optional<Integer> firstResult, Optional<Integer> maxResults,
										 Pair<String, ?>... args) {
		TypedQuery<T> query1 = query;
		for (Pair<String, ?> arg : args) {
			query1 = query1.setParameter(arg.getKey(), arg.getValue());
		}
		query1.setFirstResult(firstResult.or(0));
		query1.setMaxResults(maxResults.or(10));
		List<T> resultList = query1.getResultList();
		return resultList;
	}

	protected final List<T> executeQuery(TypedQuery<T> query,
										 Optional<Integer> firstResult, Optional<Integer> maxResults,
										 Map<String, ?> args) {
		TypedQuery<T> query1 = query;
		setQueryParams(query1, args);
		query1.setFirstResult(firstResult.or(0));
		query1.setMaxResults(maxResults.or(10));
		List<T> resultList = query1.getResultList();
		return resultList;
	}


	@SafeVarargs
	protected final List<T> executeQuery(CriteriaQuery<T> criteriaQuery,
										 Optional<Integer> firstResult, Optional<Integer> maxResults,
										 Pair<String, ?>... args) {
		return executeQuery(entityManager.createQuery(criteriaQuery), firstResult, maxResults, args);
	}

	protected final List<T> executeQuery(CriteriaQuery<T> criteriaQuery,
										 Optional<Integer> firstResult, Optional<Integer> maxResults,
										 Map<String, ?> args) {
		return executeQuery(entityManager.createQuery(criteriaQuery), firstResult, maxResults, args);
	}

	@SuppressWarnings("unchecked")
	protected static Predicate getLike(CriteriaBuilder cb, String alias, Path pathRoot) {
		return cb.like(cb.upper(pathRoot.get(alias)), cb.upper(cb.parameter(String.class, alias)));
	}

	//For f*cks sake, Oracle, please get your *** api together.
	protected Integer executeNamedQueryToCount(String queryName) {
		return ((Number) entityManager.createNamedQuery(queryName).getSingleResult()).intValue();
	}

	protected Integer executeCriteriaQueryToCount(CriteriaQuery queryName, Map<String, String> filter) {
		TypedQuery query = entityManager.createQuery(queryName);
		query = setQueryParams(query, filter);
		return ((Number) query.getSingleResult()).intValue();
	}

	private static TypedQuery setQueryParams(TypedQuery query, Map<String, ?> filter) {
		for (Map.Entry<String, ?> arg : filter.entrySet()) {
			query = query.setParameter(arg.getKey(), arg.getValue());
		}
		return query;
	}

	private TypedQuery<T> getTypedNamedQuery(String queryName) {
		return entityManager.createNamedQuery(queryName, entityClass);
	}

	protected List<Predicate> buildPredicates(Map<String, String> filter, CriteriaBuilder criteriaBuilder, Path<T> book) {
		List<Predicate> predicates = new ArrayList<>();
		for (Map.Entry<String, String> filter1 : filter.entrySet()) {
			if (StringUtils.isEmpty(filter1.getValue())) {
				filter.remove(filter1.getKey());
			}
			String filterValue = StringUtils.appendIfMissing(StringUtils.prependIfMissing(filter1.getValue(), "%"), "%");
			filter.put(filter1.getKey(), filterValue);
			predicates.add(getLike(criteriaBuilder, filter1.getKey(), book));
		}
		return predicates;
	}

	protected List<Order> getOrders(Map<String, Boolean> sortingOrder, CriteriaBuilder criteriaBuilder, Root<T> root) {
		List<Order> sortingList = Lists.newArrayList();
		for (Map.Entry<String, Boolean> sortingEntry : sortingOrder.entrySet()) {
			Path<T> sortingPath = root.get(sortingEntry.getKey());
			sortingList.add(sortingEntry.getValue() ? criteriaBuilder.asc(sortingPath) : criteriaBuilder.desc((sortingPath)));
		}
		return sortingList;
	}
}
