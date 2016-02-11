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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bandr on 01.02.2016.
 */
@ManagedBean
@ViewScoped
public class BookEditController extends AbstractBookModicicationController {
	private static final Logger LOG = LoggerFactory.getLogger(BookEditController.class);
	private Long id;

	public void init() {
		this.book = bookManager.getByIdWithInitializedCollections(id);
		authorModel = new AuthorAbstractLazyDataModel(this.book.getAuthors());
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
