package ua.softserve.bandr.web.controller.author;

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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bandr on 02.02.2016.
 */
@ManagedBean
@ViewScoped
public class AuthorEditController {
	@Inject
	private AuthorManager authorManager;
	@Inject
	private BookManager bookManager;
	private static final Logger LOG = LoggerFactory.getLogger(AuthorEditController.class);

	// FIXME: 08.02.2016 add caching for book data
	private Long id;
	private Author author = new Author();
	private AbstractLazyDataModel<Book> bookModel = new BookAbstractLazyDataModel();
	private Set<Object> selected = Sets.newHashSet();
	private String addBookData;

	public void init() {
		author = authorManager.getByIdWithInitializedCollections(id);
		bookModel = new BookAbstractLazyDataModel(author.getBooks());
	}

	public List<Book> autocomplete(String prefix) {
		List<Book> bookByPrefix = bookManager.getBookByPrefix(prefix);
		Iterables.removeIf(bookByPrefix, new BookPredicate(selected));
		return bookByPrefix;
	}

	public void removeBooks() {
		LOG.info("Removing selected books from author [{}]", selected);
		Iterables.removeIf(author.getBooks(), new BookPredicate(selected));
		bookModel = new BookAbstractLazyDataModel(author.getBooks());
	}

	public void addBook() {
		LOG.info("Add books");
		Book selectedBook = bookManager.getBookByISBN(addBookData);
		author.getBooks().add(selectedBook);
		bookModel = new BookAbstractLazyDataModel(author.getBooks());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Set<Object> getSelected() {
		return selected;
	}

	public void setSelected(Set<Object> selected) {
		this.selected = selected;
	}

	private final class BookPredicate implements Predicate<Book> {
		private final Set<Object> selected;

		private BookPredicate(Set<Object> selected) {
			this.selected = selected;
		}

		@Override
		public boolean apply(Book input) {
			return this.selected.contains(input.getId());
		}
	}

	public AbstractLazyDataModel<Book> getBookModel() {
		return bookModel;
	}

	public void setBookModel(AbstractLazyDataModel<Book> bookModel) {
		this.bookModel = bookModel;
	}

	public void save() {
		try {
			authorManager.update(author);
		} catch (ConstraintCheckException e) {
			FacesContext.getCurrentInstance().addMessage("authorData:firstName", new FacesMessage("Author with this first name and last name are already in database"));
		}
	}

	public String getAddBookData() {
		return addBookData;
	}

	public void setAddBookData(String addBookData) {
		this.addBookData = addBookData;
	}

	private final class BookAbstractLazyDataModel extends AbstractLazyDataModel<Book> {

		private List<Book> books = new ArrayList<>();

		private BookAbstractLazyDataModel(Set<Book> books) {
			this.books = new ArrayList<>(books);
		}

		private BookAbstractLazyDataModel() {
		}

		@Override
		public List<Book> getDataList(int firstRow, int numRows, Map<String, String> filter, Map<String, SortOrder> sorting) {
			return books.subList(firstRow, (firstRow + numRows) < books.size() ? (firstRow + numRows) : books.size());
		}

		@Override
		public Object getKey(Book book) {
			return book.getId();
		}

		@Override
		public Long getTotalCount(Map<String, String> filter) {
			return (long) books.size();
		}

	}
}