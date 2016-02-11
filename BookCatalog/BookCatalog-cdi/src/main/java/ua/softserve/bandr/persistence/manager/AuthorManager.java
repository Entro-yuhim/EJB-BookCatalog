package ua.softserve.bandr.persistence.manager;

import com.google.common.base.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Equator;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;
import ua.softserve.bandr.persistence.exceptions.InvalidEntityStateException;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.AuthorFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.AuthorHome;
import ua.softserve.bandr.utils.ValidateArgument;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collection;
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
		ValidateArgument.notNull(entity, "Received null argument in AuthorManager#persist");
		if (authorFacade.authorExists(entity.getFirstName(), entity.getLastName())) {
			throw new ConstraintCheckException("Author already exists.");
		}
		return super.persist(entity);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author update(@Valid Author entity) throws ConstraintCheckException {
		ValidateArgument.notNull(entity, "Received null argument in AuthorManager#update");
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
		Collection<Book> removed = CollectionUtils.removeAll(mergeTo.getBooks(), mergeFrom.getBooks(), new BookEquator());
		Collection<Book> added = CollectionUtils.removeAll(mergeFrom.getBooks(), mergeTo.getBooks(), new BookEquator());
		bookManager.removeAuthorFromBooks(mergeFrom, removed);
		bookManager.addAuthorToBooks(mergeFrom, added);
		return mergeTo;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author getByIdWithInitializedCollections(Long id) {
		ValidateArgument.notNull(id, "Received null argument in AuthorManager#getByIdWithInitializedCollections");
		Author author = super.getById(id);
		author.getBooks().size();
		return author;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Author getByFullName(String firstName, String lastName) {
		ValidateArgument.notNull(firstName, "Received null argument[firstName] in AuthorManager#getByFullName");
		return authorFacade.getByFullName(firstName, lastName);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Author author) throws ConstraintCheckException {
		ValidateArgument.notNull(author, "Received impossible null argument[author] in AuthorManager#delete(Author)");
		Author authorDB = authorFacade.getById(author.getId());
		if (!authorDB.getBooks().isEmpty()) {
			throw new ConstraintCheckException("Unable to delete author - books still present.");
		}
		authorHome.delete(author.getId());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) throws ConstraintCheckException {
		ValidateArgument.notNull(id, "Received impossible null[id] argument in AuthorManager#delete(Long)");
		Author authorDB = authorFacade.getById(id);
		if (authorDB.getBooks().isEmpty()) {
			authorHome.delete(authorDB);
		}
		throw new ConstraintCheckException("Author still has books.");
		/* fixme Should do something if validation fails; */
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAllById(Collection<Long> idCollection) throws ConstraintCheckException {
		ValidateArgument.notNull(idCollection, "Received impossible null[idCollection] argument in AuthorManager#deleteAllById(Collection)");
		for (Long id : idCollection) {
			delete(id);
		}
	}

	public List<Author> getByName(String name) {
		ValidateArgument.notNull(name, "Received null argument in AuthorManager#getByName");
		return authorFacade.getByName(name);
	}

	public boolean willCauseCollision(Author author) {
		Author byFullName = authorFacade.getByFullName(author.getFirstName(), author.getLastName());
		return !(byFullName == null || byFullName.getId().equals(author.getId()));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean canBeDeleted(Long id) {
		ValidateArgument.notNull(id, "Received null [id] argument in AuthorManager#canBeDeleted");
		return getById(id).getBooks().isEmpty();
	}

	private static class BookEquator implements Equator<Book> {
		@Override
		public boolean equate(Book o1, Book o2) {
			if (o1.getId() == null || o2.getId() == null) {
				return Objects.equal(o1.getiSBN(), o2.getiSBN());
			} else {
				return o1.getId().equals(o2.getId());
			}
		}

		@Override
		public int hash(Book o) {
			return Objects.hashCode(o.getiSBN());
		}
	}
}

