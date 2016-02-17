package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class BookHome extends AbstractHome<Book> {
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long delete(Long id) {
		executeDeleteQuery(Book.DELETE_BY_ID, id);
		return id;
	}
}
