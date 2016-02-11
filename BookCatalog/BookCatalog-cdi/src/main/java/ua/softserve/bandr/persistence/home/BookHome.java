package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class BookHome extends AbstractHome<Book> {
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void delete(Long id) {
		executeDeleteQuery(Book.DELETE_BY_ID, id);
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Book addAuthorToBook(Author author, Book book) {
		Book book1 = entityManager.find(Book.class, book.getId());
		author = entityManager.merge(author);
		book1.getAuthors().size();
		book1.getAuthors().add(author);
		return book1;
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Book removeAuthorFromBook(Author author, Book book) {
		Book book1 = entityManager.find(Book.class, book.getId());
		Set<Author> authors = book1.getAuthors();
		//using iterator to remove concurrent access issues with hibernate.
		for (Iterator<Author> iterator = authors.iterator(); iterator.hasNext(); ) {
			Author author1 = iterator.next();
			if (author1.getId().equals(author.getId())) {
				iterator.remove();
			}
		}
		return book;
	}

}
