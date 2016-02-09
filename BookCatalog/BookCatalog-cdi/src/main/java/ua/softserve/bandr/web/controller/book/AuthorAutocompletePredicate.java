package ua.softserve.bandr.web.controller.book;

import com.google.common.base.Predicate;
import ua.softserve.bandr.entity.Author;

import java.util.Collection;


/**
 * Created by bandr on 09.02.2016.
 */
final class AuthorAutocompletePredicate implements Predicate<Author> {
	private Collection<Author> authors;

	AuthorAutocompletePredicate(Collection<Author> authors) {
		this.authors = authors;
	}

	@Override
	public boolean apply(Author input) {
		for (Author a : authors) {
			if (input.getId().equals(a.getId())) {
				return true;
			}
		}
		return false;
	}
}
