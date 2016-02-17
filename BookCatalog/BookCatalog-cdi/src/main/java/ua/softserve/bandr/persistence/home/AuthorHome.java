package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
public class AuthorHome extends AbstractHome<Author> {
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long delete(Long id) {
		executeDeleteQuery(Author.DELETE_BY_ID, id);
		return id;
	}
}
