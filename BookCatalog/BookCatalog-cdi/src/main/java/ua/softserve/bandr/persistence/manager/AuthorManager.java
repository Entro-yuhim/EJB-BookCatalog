package ua.softserve.bandr.persistence.manager;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.Validate;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;
import ua.softserve.bandr.persistence.exceptions.InvalidEntityStateException;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.AuthorFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.AuthorHome;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class AuthorManager extends AbstractManager<Author> {

	@Inject
	private AuthorHome authorHome;
	@Inject
	private AuthorFacade authorFacade;
	@Inject
	private BookManager bookManager;

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
		if (authorFacade.authorExists(entity.getFirstName(), entity.getLastName())) {
			throw new ConstraintCheckException("Author already exists.");
		}
		return super.persist(entity);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author update(@Valid Author entity) throws ConstraintCheckException {
		Validate.notNull(entity, "Received null argument in AuthorManager#update");
		if (entity.getId() == null) {
			throw new InvalidEntityStateException("Entity with null ID cannot be valid argument for update statement");
		}
		if (!willCauseCollision(entity)) {
			updateBooks(entity, getById(entity.getId()));
			return super.update(entity);
		}
		throw new ConstraintCheckException("Author with these firstName and lastName already exist.");
	}

	private Author updateBooks(Author mergeFrom, Author mergeTo) throws ConstraintCheckException {
		mergeFrom.getBooks().size();
		Set<Book> removed = Sets.difference(mergeTo.getBooks(), mergeFrom.getBooks());
		Set<Book> added = Sets.difference(mergeFrom.getBooks(), mergeTo.getBooks());
		bookManager.removeAuthorFromBooks(mergeFrom, removed);
		bookManager.addAuthorToBooks(mergeFrom, added);
		return mergeTo;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author getByIdWithInitializedCollections(Long id) {
		Author author = super.getById(id);
		author.getBooks().size();
		return author;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Author getByFullName(String firstName, String lastName) {
		return authorFacade.getByFullName(firstName, lastName);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Author author) throws ConstraintCheckException {
		Validate.notNull(author, "Received impossible null argument in AuthorManager#delete");
		Author authorDB = authorFacade.getById(author.getId());
		if (!authorDB.getBooks().isEmpty()) {
			throw new ConstraintCheckException("Unable to delete author - books still present.");
		}
		authorHome.delete(author.getId());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		Validate.notNull(id, "Received impossible null argument in AuthorManager#delete");
		Author authorDB = authorFacade.getById(id);
		if (authorDB.getBooks().isEmpty()) {
			authorHome.delete(authorDB);
		}
		/* fixme Should do something if validation fails; */
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAllById(Collection<Long> idCollection) {
		for (Long id : idCollection) {
			delete(id);
		}
	}

	public List<Author> getByName(String name) {
		Validate.notNull(name, "Received null argument in AuthorManager#getByName");
		return authorFacade.getByName(name);
	}

	public List<Author> getByBookId(Long id) {
		Validate.notNull(id, "Received null argument in AuthorManager#getByBookId");
		return authorFacade.getByBookId(id);
	}

	public boolean willCauseCollision(Author author) {
		Author byFullName = authorFacade.getByFullName(author.getFirstName(), author.getLastName());
		return !(byFullName == null || byFullName.getId().equals(author.getId()));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean canBeDeleted(Long id) {
		return getById(id).getBooks().size() == 0;
	}
}

