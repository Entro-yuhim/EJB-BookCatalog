package ua.softserve.bandr.web.controller.book;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.persistence.manager.ConstraintCheckException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by bandr on 03.02.2016.
 */
@ManagedBean
@ViewScoped
public class BookCreateController {
	@Inject
	private BookManager bookManager;
	@Inject
	private AuthorManager authorManager;

	private List<Author> authors = Lists.newArrayList();
	private static final Logger LOG = LoggerFactory.getLogger(BookCreateController.class);
	private Book book = new Book();
	private String selectedAuthor;

	//TODO remove duplicates. In some way.
	public Collection<Author> autocomplete(FacesContext facesContext, UIComponent component, String prefix) {
		return authorManager.getByName(prefix);
	}

	public void save() {
		LOG.info("Got a book with title [{}]", book.getTitle());
		book.setAuthors(new HashSet<>(authors));
		try {
			bookManager.persist(book);
		} catch (ConstraintCheckException e) {
			LOG.error("UNHANDLED EXCEPTION", e);
			LOG.info("Error when persisting book", e);
		}
	}

	public void addSelectedAuthor() {
		LOG.info("Got author name [{}]", selectedAuthor);
		if (StringUtils.isEmpty(selectedAuthor)) {
			return;
		}
		List<Author> byName = authorManager.getByName(selectedAuthor);
		if (byName.isEmpty()) {
			LOG.info("Author is missing, notify user");
			return;
		} else {
			authors.addAll(byName);
		}
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getSelectedAuthor() {
		return selectedAuthor;
	}

	public void setSelectedAuthor(String selectedAuthor) {
		this.selectedAuthor = selectedAuthor;
	}

}
