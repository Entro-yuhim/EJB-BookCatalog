package ua.softserve.bandr.web.controller.author;

import com.google.common.base.Predicate;
import ua.softserve.bandr.entity.Book;

import java.util.Set;

/**
 * Created by bandr on 09.02.2016.
 */
final class BookPredicate implements Predicate<Book> {
	private final Set<Object> selected;

	BookPredicate(Set<Object> selected) {
		this.selected = selected;
	}

	@Override
	public boolean apply(Book input) {
		return this.selected.contains(input.getId());
	}
}
