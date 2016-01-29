package ua.softserve.bandr.persistence.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.BookFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.BookHome;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BookManager extends AbstractManager<Book> {
	@Inject
	private BookHome bookHome;
	@Inject
	private BookFacade bookFacade;

	private static final Logger LOG = LoggerFactory.getLogger(BookManager.class);

	@Override
	protected AbstractHome<Book> getHome() {
		return bookHome;
	}

	@Override
	protected AbstractFacadeInt<Book> getFacade() {
		return bookFacade;
	}

	public List<BookRatingDTO> getBookRatingData() {
		LOG.debug("Retrieved data of book distribution by rating.");
		return bookFacade.getBookCountByRating();
	}
}
