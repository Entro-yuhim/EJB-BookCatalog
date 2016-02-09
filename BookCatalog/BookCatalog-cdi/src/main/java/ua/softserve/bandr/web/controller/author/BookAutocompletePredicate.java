package ua.softserve.bandr.web.controller.author;

import com.google.common.base.Predicate;
import ua.softserve.bandr.entity.Book;

import java.util.Set;

/**
 * Created by bandr on 09.02.2016.
 */
final class BookAutocompletePredicate implements Predicate<Book> {
	private final Set<Book> books;

	BookAutocompletePredicate(Set<Book> books) {
		this.books = books;
	}

	@Override
	public boolean apply(Book input) {
		for (Book book : books) {
			if (input.getId().equals(book.getId())) {
				return true;
			}
		}
		return false;
	}
}
