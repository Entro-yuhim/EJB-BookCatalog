package ua.softserve.bandr.persistence.facade;

import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Book;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 22.01.2016.
 */
@Local
public interface BookFacade extends AbstractFacadeInt<Book> {

	List<Book> getAllByAuthor(String authorFilter);

	List<Book> getBooksByRating(int rating);

	List<BookRatingDTO> getBookCountByRating();
}
