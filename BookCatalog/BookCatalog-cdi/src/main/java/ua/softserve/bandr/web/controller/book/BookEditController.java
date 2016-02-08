package ua.softserve.bandr.web.controller.book;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.web.pagination.richmodels.AbstractLazyDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bandr on 01.02.2016.
 */
@ManagedBean
@ViewScoped
public class BookEditController {
	private static final Logger LOG = LoggerFactory.getLogger(BookEditController.class);
	@Inject
	private BookManager bookManager;
	@Inject
	private AuthorManager authorManager;

	private Long id;
	private Book book = new Book();
	private AbstractLazyDataModel<Author> authorModel = new AuthorAbstractLazyDataModel();
	private Set<Object> selected = Sets.newHashSet();
	private String addAuthorData;

	public void init() {
		this.book = bookManager.getByIdWithInitializedCollections(id);
		authorModel = new AuthorAbstractLazyDataModel(this.book.getAuthors());
	}

	public List<Author> autocomplete(String prefix) {
		List<Author> bookByPrefix = authorManager.getByName(prefix == null ? "" : prefix);
		return bookByPrefix;
	}

	public void removeAuthors() {
		LOG.info("Removing selected books from author [{}]", selected);
		Iterables.removeIf(book.getAuthors(), new AuthorPredicate(selected));
		authorModel = new AuthorAbstractLazyDataModel(book.getAuthors());
	}

	public void addAuthor() {
		LOG.info("Add books");
		Author selectedBook = authorManager.getByFullName(getFirstName(addAuthorData), getLastName(addAuthorData));
		book.getAuthors().add(selectedBook);
		authorModel = new AuthorAbstractLazyDataModel(book.getAuthors());
	}

	public void save() {
		try {
			bookManager.update(book);
		} catch (ConstraintCheckException e) {
			e.printStackTrace();
		}
	}

	private String getFirstName(String addAuthorData) {
		if (addAuthorData.split(" ").length < 1) {
			return "";
		}
		return addAuthorData.split(" ")[0];
	}

	private String getLastName(String addAuthorData) {
		if (addAuthorData.split(" ").length < 2) {
			return "";
		}
		return addAuthorData.split(" ")[1];
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	private static final class AuthorAbstractLazyDataModel extends AbstractLazyDataModel<Author> {

		private List<Author> authors = new ArrayList<>();

		private AuthorAbstractLazyDataModel(Set<Author> authors) {
			this.authors = new ArrayList<>(authors);
		}

		private AuthorAbstractLazyDataModel() {
		}

		@Override
		public List<Author> getDataList(int firstRow, int numRows, Map<String, String> filter, Map<String, SortOrder> sorting) {
			return authors.subList(firstRow, (firstRow + numRows) < authors.size() ? (firstRow + numRows) : authors.size());
		}

		@Override
		public Object getKey(Author author) {
			return author.getId();
		}

		@Override
		public Long getTotalCount(Map<String, String> filter) {
			return (long) authors.size();
		}

	}

	private final class AuthorPredicate implements Predicate<Author> {
		private final Set<Object> selected;

		private AuthorPredicate(Set<Object> selected) {
			this.selected = selected;
		}

		@Override
		public boolean apply(Author input) {
			return this.selected.contains(input.getId());
		}
	}
}
