package ua.softserve.bandr.persistence.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;
import ua.softserve.bandr.persistence.exceptions.InvalidEntityStateException;
import ua.softserve.bandr.persistence.exceptions.PersistenceException;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.BookFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.BookHome;
import ua.softserve.bandr.utils.ValidateArgument;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED) // todo why?
public class BookManager extends AbstractManager<Book> {
	@Inject
	private BookHome bookHome;
	@Inject
	private BookFacade bookFacade;

	private static final Logger LOG = LoggerFactory.getLogger(BookManager.class);

	@Override
	public Long persist(@Valid Book entity) throws PersistenceException {
		ValidateArgument.notNull(entity, "Received null [entity] argument in BookManager#persist");
		if (isISBNPresent(entity.getiSBN())) {
			throw new ConstraintCheckException("Book with such ISBN is already persisted.");
		}
		return super.persist(entity);
	}

	@Override
	public Book update(@Valid Book entity) throws PersistenceException {
		ValidateArgument.notNull(entity, "Received null argument in BookManager#update");
		if (entity.getId() == null) {
			throw new InvalidEntityStateException("Entity with null ID cannot be valid argument for update statement");
		}
		if (isISBNPresent(entity.getiSBN()) && !entity.getId().equals(getBookByISBN(entity.getiSBN()).getId())) {
			throw new ConstraintCheckException("Book with such ISBN is already persisted.");
		}
		return super.update(entity);
	}

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

	public List<Book> getByAuthorId(Long id) {
		ValidateArgument.notNull(id, "Received null argument in BookManager#getByAuthorId");
		return bookFacade.getAllByAuthor(id);
	}

	public List<Book> getBookByPrefix(String prefix) {
		ValidateArgument.notNull(prefix, "Received null [prefix] argument in BookManager#getBookByPrefix");
		return bookFacade.getByNameOrISBN(prefix);
	}

	public Book getBookByISBN(String isbn) {
		ValidateArgument.notNull(isbn, "Received null [isbn] argument in BookManager#getBookByISBN");
		return bookFacade.getByISBN(isbn);
	}

	public Book getByIdWithInitializedCollections(Long id) {
		ValidateArgument.notNull(id, "Received null [id] argument in BookManager#getByIdWithInitializedCollections");
		Book byId = super.getById(id);
		byId.getReviews().size();
		byId.getAuthors().size();
		return byId;
	}

	public Boolean isISBNPresent(String isbn) {
		ValidateArgument.notNull(isbn, "Received null [isbn] argument in BookManager#isISBNPresent");
		return bookFacade.isISBNPresent(isbn);
	}

	@Override
	public List<Book> getPagedFiltered(Integer firstRow, Integer numRows, Map<String, String> filter, Map<String, Boolean> sortingOrder) {
		//Validate for nulls in super call
		List<Book> pagedFiltered = super.getPagedFiltered(firstRow, numRows, filter, sortingOrder);
		for (Book book : pagedFiltered) {
			book.getAuthors().size();
		}
		return pagedFiltered;
	}

	public Set<Book> removeAuthorFromBooks(Author author, Set<Book> books) throws PersistenceException {
		ValidateArgument.notNull(author);
		ValidateArgument.notNull(books);
		for (Iterator<Book> bookIterator = books.iterator(); bookIterator.hasNext(); ) { // todo ?
			Book b = getById(bookIterator.next().getId());
			for (Iterator<Author> authorIterator = b.getAuthors().iterator(); authorIterator.hasNext(); ) {
				if (authorIterator.next().getId().equals(author.getId())) {
					bookIterator.remove();
				}
			}
			update(b);
		}
		return books;
	}

	public Set<Book> addAuthorToBooks(Author author, Set<Book> books) throws PersistenceException {
		ValidateArgument.notNull(author);
		ValidateArgument.notNull(books);
		for (Iterator<Book> bookIterator = books.iterator(); bookIterator.hasNext(); ) {
			Book b = getById(bookIterator.next().getId());
			b.getAuthors().add(author);
			update(b);
		}
		return books;
	}
}
