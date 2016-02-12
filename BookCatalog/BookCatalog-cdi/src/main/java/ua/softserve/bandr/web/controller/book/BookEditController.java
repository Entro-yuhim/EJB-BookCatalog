package ua.softserve.bandr.web.controller.book;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.IOException;
import java.util.List;

/**
 * Created by bandr on 01.02.2016.
 */
@ManagedBean
@ViewScoped
public class BookEditController extends AbstractBookModicicationController {
	private static final Logger LOG = LoggerFactory.getLogger(BookEditController.class);
	private Long id = 26L;

	private String authorFilter = "";

	private List<Author> authorsAutocomplete = Lists.newArrayList();
	private List<Author> authorsSelected = Lists.newArrayList();

	public void init() {
		this.book = bookManager.getByIdWithInitializedCollections(id);
		authorModel = new AuthorAbstractLazyDataModel(this.book.getAuthors());
		updateAuthorList("");
		authorsSelected.addAll(book.getAuthors());
		authorsAutocomplete.addAll(authorsSelected);
	}

	private void updateAuthorList(String filter) {
		authorsAutocomplete = Lists.newArrayList(authorManager.getByName(filter == null ? "" : filter));
	}

	public void save() throws IOException {
		LOG.info("Updating Book with id [{}] with new data", book.getId());
		try {
			bookManager.update(book);
			FacesContext.getCurrentInstance().getExternalContext().redirect("bookPage.jsf");
		} catch (ConstraintCheckException e) {
			LOG.warn("Collision attempting to persist book object [{}]", book.getId());
			FacesContext.getCurrentInstance()
					.addMessage("bookData:isbn",
							new FacesMessage(String.format("Book with this ISBN %s already exists", book.getiSBN())));
		}
	}

	public void removeAuthors() {
		//List<Author> allById = authorManager.getAllById(authorsSelected);
		LOG.info("");
		book.setAuthors(Sets.newHashSet(authorsSelected));
		authorModel = new AuthorAbstractLazyDataModel(book.getAuthors());

	}

	public List<Author> getAuthorsAutocomplete() {
		return authorsAutocomplete;
	}

	public void setAuthorsAutocomplete(List<Author> authorsAutocomplete) {
		this.authorsAutocomplete = authorsAutocomplete;
	}

	public List<Author> getAuthorsSelected() {
		LOG.info("List is populated: [{}]", authorsSelected.isEmpty());
		return authorsSelected;
	}

	public void setAuthorsSelected(List<Author> authorsSelected) {
		this.authorsSelected = authorsSelected;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthorFilter() {
		return authorFilter;
	}

	public void setAuthorFilter(String authorFilter) {
		this.authorFilter = authorFilter;
	}

	public void filterChange(ValueChangeEvent valueChangeEvent) {
		Object newValue = valueChangeEvent.getNewValue();
		if (newValue != null && !"".equals(newValue) && newValue instanceof String) {
			updateAuthorList((String) newValue);
		}
	}
}
