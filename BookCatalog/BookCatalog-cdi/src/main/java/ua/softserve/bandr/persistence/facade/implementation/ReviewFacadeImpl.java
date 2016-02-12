package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.ReviewFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless
// todo @TransactionAttribute?
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReviewFacadeImpl extends AbstractFacade<Review> implements ReviewFacade {

	private static final Logger LOG = LoggerFactory.getLogger(ReviewFacadeImpl.class);

	public ReviewFacadeImpl() {
		super(Review.class);
	}

	@Override
	public List<Review> getAll() {
		List<Review> reviews = executeNamedQuery(Review.GET_ALL);
		LOG.debug("Got all reviews, {} total", reviews.size());
		return reviews;
	}

	//Not used
	@Override
	public List<Review> getPagedFilteredSorted(Integer startWith, Integer pageSize, Map<String, String> filterText, Map<String, Boolean> sortingOrder) {
		return null;
	}

	@Override
	public Long getRecordCount(Map<String, String> filter) {
		return executeNamedQueryToCount(Review.GET_RECORD_COUNT);
	}

	@Override
	public List<Review> getByBookId(Long bookId) {
		return executeNamedQuery(Review.GET_BY_BOOK, Pair.of("id", bookId));
	}
}
