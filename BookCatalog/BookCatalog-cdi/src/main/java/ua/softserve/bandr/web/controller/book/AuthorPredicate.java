package ua.softserve.bandr.web.controller.book;

import com.google.common.base.Predicate;
import ua.softserve.bandr.entity.Author;

import java.util.Set;

final class AuthorPredicate implements Predicate<Author> {
	private final Set<Object> selected;

	AuthorPredicate(Set<Object> selected) {
		this.selected = selected;
	}

	@Override
	public boolean apply(Author input) {
		return this.selected.contains(input.getId());
	}
}