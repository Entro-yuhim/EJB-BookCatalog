package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class ReviewHome extends AbstractHome<Review> {
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		executeDeleteQuery(Review.DELETE_BY_ID, id);
	}
}
