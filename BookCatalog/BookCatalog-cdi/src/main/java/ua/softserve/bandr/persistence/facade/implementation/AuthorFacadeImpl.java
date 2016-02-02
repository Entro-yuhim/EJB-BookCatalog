package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.facade.AuthorFacade;

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
		return executeNamedQuery(Author.GET_ALL, Optional.of(startWith), Optional.of(pageSize));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Integer getRecordCount(Map<String, String> filter) {
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
				Pair.of("lastName", (Object) lastName), Pair.of("firstName", lastName));
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
}
