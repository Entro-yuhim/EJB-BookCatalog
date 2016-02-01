package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
	public Integer getRecordCount() {
		return executeNamedQueryToCount(Book.GET_RECORD_COUNT);
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

	//I need to see what type of data will I receive from JSF/RF for pagination to properly implement this
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Book> getPagedFilteredSorted(Integer startWith, Integer pageSize,
											 List<Pair<String, Object>> filterData) {//, Optional<Book.BookSorting> sorting) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Book> criteria = criteriaBuilder.createQuery(Book.class);
		Root<Book> book = criteria.from(Book.class);
		Join<Book, Author> bookAuthor = book.join("authors");
		//bookAuthor.get("title");
		List<Predicate> predicates = new ArrayList<>();
		//This api sucks // FIXME: 01.02.2016
		for (Pair<String, Object> filter : filterData) {
			//TODO check actual name of filter
			predicates.add(getLike2(criteriaBuilder, filter.getKey(), "authorNames".equals(filter.getKey()) ? bookAuthor : book));
		}
		CriteriaQuery<Book> finalQuery =
				criteria.select(book)
						.where(criteriaBuilder
								.or(getLike(criteriaBuilder, "alias", book.get("title")),
										getLike(criteriaBuilder, "alias", bookAuthor.get("firstName")),
										getLike(criteriaBuilder, "alias", bookAuthor.get("lastName"))));
		return executeQuery(finalQuery, Optional.of(startWith), Optional.of(pageSize), Pair.of("alias", "%" + "abc".toUpperCase() + "%")); // todo: case unsensitive ?
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
}
