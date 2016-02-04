package ua.softserve.bandr.persistence.facade;


import ua.softserve.bandr.entity.Author;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
@Local
public interface AuthorFacade extends AbstractFacadeInt<Author> {
	public List<Author> getByName(String name);

	List<Author> getByBookId(Long id);

	Author getByFullName(String firstName, String lastName);
}
