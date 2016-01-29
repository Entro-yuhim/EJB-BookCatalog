package ua.softserve.bandr.persistence.facade;


import com.google.common.base.Optional;
import ua.softserve.bandr.entity.Author;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
@Local
public interface AuthorFacade extends AbstractFacadeInt<Author> {
	public List<Author> getByName(String name);

	public List<Author> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
											   Optional<String> filterText);
}
