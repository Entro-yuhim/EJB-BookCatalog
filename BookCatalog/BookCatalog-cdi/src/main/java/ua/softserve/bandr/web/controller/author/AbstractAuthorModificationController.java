package ua.softserve.bandr.web.controller.author;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.web.pagination.richmodels.AbstractLazyDataModel;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by bandr on 09.02.2016.
 */
public abstract class AbstractAuthorModificationController {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractAuthorModificationController.class);
	@Inject
	protected AuthorManager authorManager;
	@Inject
	protected BookManager bookManager;
	protected Author author = new Author();
	protected AbstractLazyDataModel<Book> bookModel = new BookAbstractLazyDataModel();
	protected Set<Object> selected = Sets.newHashSet();
	protected String addBookData;


	@AssertTrue(message = "Author already exists in database.")
	public boolean isAuthorUnique() {
		boolean b = authorManager.willCauseCollision(author);
		return !b;
	}
	public List<Book> autocomplete(String prefix) {
		List<Book> bookByPrefix = bookManager.getBookByPrefix(prefix);
		Iterables.removeIf(bookByPrefix, new BookAutocompletePredicate(author.getBooks()));
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

	public abstract void save() throws IOException;

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

	public AbstractLazyDataModel<Book> getBookModel() {
		return bookModel;
	}

	public void setBookModel(AbstractLazyDataModel<Book> bookModel) {
		this.bookModel = bookModel;
	}

	public String getAddBookData() {
		return addBookData;
	}

	public void setAddBookData(String addBookData) {
		this.addBookData = addBookData;
	}
}
