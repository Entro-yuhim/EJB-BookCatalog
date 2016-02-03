package ua.softserve.bandr.web.controller.book;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.Collection;
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
	private Author selectedAuthor = new Author();

	public Collection<Author> autocomplete(FacesContext facesContext, UIComponent component, String prefix) {
		List<Author> byName = authorManager.getByName(prefix);
		return byName;
	}

	public void save() {
		LOG.info("Got a book with title [{}]", book.getTitle());
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

	public Author getSelectedAuthor() {
		return selectedAuthor;
	}

	public void setSelectedAuthor(Author selectedAuthor) {
		this.selectedAuthor = selectedAuthor;
	}

	public void addSelectedAuthor(Long id){
		LOG.info("Doing something from ajax request");
	}
}
