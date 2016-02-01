package ua.softserve.bandr.persistence.facade;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Book;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
@Local
public interface BookFacade extends AbstractFacadeInt<Book> {

	List<Book> getAllByAuthor(String authorFilter);

	List<Book> getBooksByRating(int rating);

	List<Book> getPagedFilteredSorted(Integer startWith, Integer pageSize, List<Pair<String, Object>> filterText);

	List<BookRatingDTO> getBookCountByRating();
}
