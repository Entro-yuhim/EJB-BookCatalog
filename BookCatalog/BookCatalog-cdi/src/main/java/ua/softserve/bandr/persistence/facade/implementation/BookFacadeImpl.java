package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.facade.BookFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class BookFacadeImpl extends AbstractFacade<Book> implements BookFacade {

	public BookFacadeImpl() {
		super(Book.class);
	}

	@Override
	public List<Book> getAll() {
		return executeNamedQuery(Book.GET_ALL);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Book> getPaged(Integer startWith, Integer pageSize) {
		return executeNamedQuery(Book.GET_ALL, Optional.of(startWith), Optional.of(pageSize));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Integer getRecordCount(Map<String, String> filter) {
		if (filter == null || filter.isEmpty()) {
			return executeNamedQueryToCount(Book.GET_RECORD_COUNT);
		}
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Long> baseQuery = criteriaBuilder.createQuery(Long.class);
		Root<Book> book = baseQuery.from(Book.class);
		Join<Book, Author> bookAuthor = book.join("authors");
		List<Predicate> predicates = buildFullBookPredicates(filter, criteriaBuilder, book, bookAuthor);


		CriteriaQuery<Long> bookCriteriaQuery = baseQuery.select(criteriaBuilder.count(book))
				.where(predicates.toArray(new Predicate[predicates.size()]));
		return executeCriteriaQueryToCount(bookCriteriaQuery, filter);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Book> getAllByAuthor(String authorFilter) {
		return executeNamedQuery(Book.GET_BY_AUTHOR_NAME,
				Pair.of("authorNamePlaceholder", authorFilter));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Book> getBooksByRating(int rating) {
		return executeNamedQuery(Book.GET_BY_RATING, Pair.of("rating", rating));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Book> getPagedFilteredSorted(Integer startWith, Integer pageSize,
											 Map<String, String> filterData, Map<String, Boolean> sortingOrder) {
		//Do something about order in which sorting is applied. not necessary right now, but might be in future;
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Book> criteria = criteriaBuilder.createQuery(Book.class);
		Root<Book> book = criteria.from(Book.class);
		Join<Book, Author> bookAuthor = book.join("authors");
		List<Predicate> predicates = buildFullBookPredicates(filterData, criteriaBuilder, book, bookAuthor);
		CriteriaQuery<Book> finalQuery = criteria.select(book)
				.where(predicates.toArray(new Predicate[predicates.size()]));
		List<Order> sortingList = getOrders(sortingOrder, criteriaBuilder, book);
		return executeQuery(finalQuery.orderBy(sortingList), Optional.of(startWith), Optional.of(pageSize), filterData);
	}

	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BookRatingDTO> getBookCountByRating() {
		List<Object[]> data = entityManager.createNamedQuery(Book.GET_COUNT_BY_RATING).getResultList();
		List<BookRatingDTO> dtoList = new ArrayList<>();
		for (Object[] a : data) {
			dtoList.add(new BookRatingDTO(((Number) a[0]).intValue(), ((Number) a[1]).longValue()));
		}
		return dtoList;
	}


	@SuppressWarnings("unchecked")
	protected static Predicate getLikeWithExactPathToParam(CriteriaBuilder cb, String alias, Path pathRoot) {
		return cb.like(cb.upper(pathRoot), cb.upper(cb.parameter(String.class, alias)));
	}


	//TODO halp.
	private static Predicate getAuthorPredicate(Map<String, String> authorParamFilter, CriteriaBuilder cb, Path<Author> authors) {
		String key = authorParamFilter.entrySet().iterator().next().getKey();
		return cb.or(getLikeWithExactPathToParam(cb, key, authors.get("firstName")),
				getLikeWithExactPathToParam(cb, key, authors.get("lastName")),
				cb.like(cb.upper(cb.concat(authors.get("firstName"), authors.get("lastName"))), cb.upper(cb.parameter(String.class, key))));
	}

	//TODO halp.
	private static void separateFiltersForBook(Map<String, String> baseSorting, Map<String, String> bookParams, Map<String, String> authorParams) {
		for (Map.Entry<String, String> filter : baseSorting.entrySet()) {
			if (filter.getValue().isEmpty()) {
				baseSorting.remove(filter.getKey());
			}
			String filterValue = StringUtils.appendIfMissing(StringUtils.prependIfMissing(filter.getValue(), "%"), "%");
			if (filter.getKey().contains("author")) {
				//this is stupid.
				authorParams.put(filter.getKey(), filterValue);
			} else {
				bookParams.put(filter.getKey(), filterValue);
			}
			baseSorting.put(filter.getKey(), filterValue);
		}
	}

	//TODO halp.
	private List<Predicate> buildFullBookPredicates(Map<String, String> filter, CriteriaBuilder criteriaBuilder, Path book, Join<Book, Author> bookAuthor) {
		Map<String, String> bookParamFilter = Maps.newHashMap();
		Map<String, String> authorParamFilter = Maps.newHashMap();
		separateFiltersForBook(filter, bookParamFilter, authorParamFilter);
		List<Predicate> predicates = buildPredicates(bookParamFilter, criteriaBuilder, book);
		if (!authorParamFilter.isEmpty()) {
			predicates.add(getAuthorPredicate(authorParamFilter, criteriaBuilder, bookAuthor));
		}
		return predicates;
	}
}
