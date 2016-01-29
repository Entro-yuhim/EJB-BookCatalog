package ua.softserve.bandr.persistence.manager;

import org.apache.commons.lang3.Validate;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.AuthorFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.AuthorHome;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class AuthorManager extends AbstractManager<Author> {

	@Inject
	private AuthorHome authorHome;
	@Inject
	private AuthorFacade authorFacade;

	@Override
	protected AbstractHome<Author> getHome() {
		return authorHome;
	}

	@Override
	protected AbstractFacadeInt<Author> getFacade() {
		return authorFacade;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Author author) {
		Validate.notNull(author);
		Author authorDB = authorHome.update(author);
		if (authorDB.getBooks().isEmpty()) {
			authorHome.delete(authorDB);
		}
		/* fixme Should do something if validation fails; */
	}
}

