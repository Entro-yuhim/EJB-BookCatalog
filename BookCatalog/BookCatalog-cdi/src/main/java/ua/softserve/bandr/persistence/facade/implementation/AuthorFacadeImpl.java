package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.facade.AuthorFacade;
import ua.softserve.bandr.utils.ValidateArgument;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AuthorFacadeImpl extends AbstractFacade<Author> implements AuthorFacade {

	public AuthorFacadeImpl() {
		super(Author.class);
	}

	@Override
	public List<Author> getAll() {
		return executeNamedQuery(Author.GET_ALL);
	}

	@Override
	public List<Author> getPaged(Integer startWith, Integer pageSize) {
		ValidateArgument.notNull(startWith, "Received null startWith as argument to AuthorFacadeImpl#getPaged");
		ValidateArgument.notNull(pageSize, "Received null pageSize as argument to AuthorFacadeImpl#getPaged");
		return executeNamedQuery(Author.GET_ALL, Optional.of(startWith), Optional.of(pageSize));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getRecordCount(Map<String, String> filter) {
		if (filter == null || filter.isEmpty()) {
			return executeNamedQueryToCount(Author.GET_RECORD_COUNT);
		}
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Long> baseQuery = criteriaBuilder.createQuery(Long.class);
		Root<Author> author = baseQuery.from(Author.class);
		List<Predicate> predicates = buildPredicates(filter, criteriaBuilder, author);
		baseQuery = baseQuery.select(criteriaBuilder.count(author))
				.where(predicates.toArray(new Predicate[predicates.size()]));
		return executeCriteriaQueryToCount(baseQuery, filter);
	}

	@Override
	public List<Author> getByName(String lastName) {
		return executeNamedQuery(Author.GET_BY_NAME,
				Pair.of("namePlaceholder", "%" + lastName + "%"));
	}

	@Override
	public List<Author> getByBookId(Long id) {
		return executeNamedQuery(Author.GET_BY_BOOK, Pair.of("bookId", id));
	}

	@Override
	public Author getByFullName(String firstName, String lastName) {
		if (authorExists(firstName, lastName)) {
			return executeNamedQueryToSingleResult(Author.GET_BY_FULL_NAME, Pair.of("firstName", firstName), Pair.of("lastName", lastName));
		}
		return null;
	}

	@Override
	public List<Author> getPagedFilteredSorted(Integer startWith, Integer pageSize, Map<String, String> filterText, Map<String, Boolean> sortingOrder) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Author> criteriaQuery = cb.createQuery(Author.class);
		Root<Author> author = criteriaQuery.from(Author.class);
		List<Predicate> predicates = buildPredicates(filterText, cb, author);
		List<Order> orderList = getOrders(sortingOrder, cb, author);
		criteriaQuery = criteriaQuery.select(author)
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(orderList);
		return executeQuery(criteriaQuery, Optional.of(startWith), Optional.of(pageSize), filterText);
	}

	@Override
	public Boolean authorExists(String firstName, String lastName) {
		return entityManager.createNamedQuery(Author.GET_COUNT_BY_NAME, Number.class)
				.setParameter("firstName", firstName.trim())
				.setParameter("lastName", lastName.trim())
				.getSingleResult().longValue() > 0;
	}
}
