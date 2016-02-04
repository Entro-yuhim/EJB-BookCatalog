package ua.softserve.bandr.persistence.manager;

import org.apache.commons.lang3.Validate;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.ReviewFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.ReviewHome;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class ReviewManager extends AbstractManager<Review> {
	@Inject
	private ReviewHome reviewHome;
	@Inject
	private ReviewFacade reviewFacade;

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
		Validate.notNull(firstRow);
		Validate.notNull(numRows);
		Validate.notNull(filter);
		Validate.notNull(sortingOrder);
		return null;
	}

	public List<Review> getByBookId(Long bookId) {
		return reviewFacade.getByBookId(bookId);
	}
}
