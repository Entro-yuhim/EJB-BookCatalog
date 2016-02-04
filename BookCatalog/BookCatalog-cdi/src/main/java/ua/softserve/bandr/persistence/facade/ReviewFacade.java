package ua.softserve.bandr.persistence.facade;

import ua.softserve.bandr.entity.Review;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
@Local
public interface ReviewFacade extends AbstractFacadeInt<Review> {
	List<Review> getByBookId(Long bookId);
}
