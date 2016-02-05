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
import javax.validation.Valid;
import java.util.List;

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
	public Long persist(@Valid Author entity) throws ConstraintCheckException {
		Validate.notNull(entity, "Received null argument in AuthorManager#persist");
		if (authorFacade.getByFullName(entity.getFirstName(), entity.getLastName()) != null) {
			throw new ConstraintCheckException("Author already exists.");
		}
		return super.persist(entity);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author getByIdWithInitializedCollections(Long id) {
		Author author = super.getById(id);
		author.getBooks().size();
		return author;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Author author) {
		Validate.notNull(author, "Received impossible null argument in AuthorManager#delete");
		Author authorDB = authorHome.update(author);
		if (authorDB.getBooks().isEmpty()) {
			authorHome.delete(authorDB);
		}
		/* fixme Should do something if validation fails; */
	}

	public List<Author> getByName(String name) {
		Validate.notNull(name, "Received null argument in AuthorManager#getByName");
		return authorFacade.getByName(name);
	}

	public List<Author> getByBookId(Long id) {
		Validate.notNull(id, "Received null argument in AuthorManager#getByBookId");
		return authorFacade.getByBookId(id);
	}

	public boolean isAuthorUnique(Author author) {
		return authorFacade.authorExists(author.getFirstName(), author.getLastName());
	}
}

