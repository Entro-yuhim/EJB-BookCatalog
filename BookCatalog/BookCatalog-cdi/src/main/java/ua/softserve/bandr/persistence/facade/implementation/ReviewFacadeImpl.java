package ua.softserve.bandr.persistence.facade.implementation;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.ReviewFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless
public class ReviewFacadeImpl extends AbstractFacade<Review> implements ReviewFacade {

	private static final Logger LOG = LoggerFactory.getLogger(ReviewFacadeImpl.class);

	protected ReviewFacadeImpl() {
		super(Review.class);
	}

	@Override
	public List<Review> getAll() {
		List<Review> reviews = executeNamedQuery(Review.GET_ALL);
		LOG.debug("Got all reviews, {} total", reviews.size());
		return reviews;
	}

	@Override
	public List<Review> getPaged(Integer startWith, Integer pageSize) {
		LOG.debug("Got paged reviews, first restult at {} max results {}", startWith, pageSize);
		return executeNamedQuery(Review.GET_ALL, Optional.of(startWith), Optional.of(pageSize));
	}

	@Override
	public List<Review> getPagedFilteredSorted(Integer startWith, Integer pageSize, Map<String, String> filterText, Map<String, Boolean> sortingOrder) {
		return null;
	}

	@Override
	public Integer getRecordCount(Map<String, String> filter) {
		return executeNamedQueryToCount(Review.GET_RECORD_COUNT);
	}
}
