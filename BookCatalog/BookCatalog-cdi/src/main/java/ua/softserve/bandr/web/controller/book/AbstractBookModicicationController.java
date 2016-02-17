package ua.softserve.bandr.web.controller.book;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.web.pagination.richmodels.AbstractLazyDataModel;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created by bandr on 09.02.2016.
 */
public abstract class AbstractBookModicicationController {
	public static final String AUTHOR_NAME_INPUT = "bookNameForm:bookName";
	@Inject
	protected BookManager bookManager;
	@Inject
	protected AuthorManager authorManager;
	private static final Logger LOG = LoggerFactory.getLogger(AbstractBookModicicationController.class);
	protected Book book = new Book();
	protected AbstractLazyDataModel<Author> authorModel = new AuthorAbstractLazyDataModel();
	protected Set<Object> selected = Sets.newHashSet();
	protected String addAuthorData;

	public Iterable<Author> autocomplete(FacesContext facesContext, UIComponent component, String prefix) {
		LOG.info("autocomplete triggers");
		List<Author> byName = authorManager.getByName(prefix == null ? "" : prefix);
		Iterables.removeIf(byName, new AuthorAutocompletePredicate(book.getAuthors()));
		return byName;
	}

	public void removeAuthors() {
		LOG.info("Removing selected books from author [{}]", selected);
		Iterables.removeIf(book.getAuthors(), new AuthorPredicate(selected));
		authorModel = new AuthorAbstractLazyDataModel(book.getAuthors());
	}

	public void addAuthor() {
		LOG.info("Add books");
		Author selectedAuthor = authorManager.getByFullName(getFirstName(addAuthorData), getLastName(addAuthorData));
		if (selectedAuthor == null) {
			LOG.info("Adding error msg to [{}]", AUTHOR_NAME_INPUT);
			FacesContext.getCurrentInstance().addMessage(AUTHOR_NAME_INPUT, new FacesMessage("Author not found, please input first and last name of author"));
		} else {
			book.getAuthors().add(selectedAuthor);
			authorModel = new AuthorAbstractLazyDataModel(book.getAuthors());
		}
	}


	private static String getFirstName(String addAuthorData) {
		if (addAuthorData.split(" ").length < 1) {
			return "";
		}
		return addAuthorData.split(" ")[0];
	}

	private static String getLastName(String addAuthorData) {
		if (addAuthorData.split(" ").length < 2) {
			return "";
		}
		return addAuthorData.split(" ")[1];
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public AbstractLazyDataModel<Author> getAuthorModel() {
		return authorModel;
	}

	public void setAuthorModel(AbstractLazyDataModel<Author> authorModel) {
		this.authorModel = authorModel;
	}

	public String getAddAuthorData() {
		return addAuthorData;
	}

	public void setAddAuthorData(String addAuthorData) {
		this.addAuthorData = addAuthorData;
	}

	public Set<Object> getSelected() {
		return selected;
	}

	public void setSelected(Set<Object> selected) {
		this.selected = selected;
	}

}
