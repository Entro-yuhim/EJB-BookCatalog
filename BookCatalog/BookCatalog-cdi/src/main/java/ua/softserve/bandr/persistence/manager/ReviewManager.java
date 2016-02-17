package ua.softserve.bandr.persistence.manager;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.Validate;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.exceptions.PersistenceException;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.ReviewFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.ReviewHome;
import ua.softserve.bandr.utils.ValidateArgument;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReviewManager extends AbstractManager<Review> {
	@Inject
	private ReviewHome reviewHome;
	@Inject
	private ReviewFacade reviewFacade;
	@Inject
	private BookManager bookManager;

	@Override
	protected AbstractHome<Review> getHome() {
		return reviewHome;
	}

	@Override
	protected AbstractFacadeInt<Review> getFacade() {
		return reviewFacade;
	}

	@Override
	public List<Review> getPagedFiltered(Integer firstRow, Integer numRows, Map<String, String> filter, Map<String, Boolean> sortingOrder) {
		throw new NotImplementedException("Should never be used");
	}

	public List<Review> getByBookId(Long bookId) {
		ValidateArgument.notNull(bookId, "Received null [bookId] argument in ReviewManager#getByBookId");
		return reviewFacade.getByBookId(bookId);
	}

	public Review persistWithBookId(Review review, Long bookId) throws PersistenceException {
		ValidateArgument.notNull(review, "Received null argument(review) in ReviewManager#persistWithBookId");
		ValidateArgument.notNull(bookId, "Received null argument(bookId) in ReviewManager#persistWithBookId");
		review.setBook(bookManager.getById(bookId));
		reviewHome.persist(review);
		return review;
	}

	public Review updateWithBookId(Review review, Long bookId) throws PersistenceException {
		ValidateArgument.notNull(review, "Received null argument(review) in ReviewManager#updateWithBookId");
		ValidateArgument.notNull(bookId, "Received null argument(bookId) in ReviewManager#updateWithBookId");

		reviewHome.update(review);
		return review;
	}
}
