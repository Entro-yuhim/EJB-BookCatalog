package ua.softserve.bandr.persistence.manager;

import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.ReviewFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.ReviewHome;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.Order;
import java.util.List;

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
	public List<Book> getPagedFiltered(Integer firstRow, Integer numRows, List<Pair<String, Object>> filterList, List<Order> orderList) {
		return null;
	}
}
