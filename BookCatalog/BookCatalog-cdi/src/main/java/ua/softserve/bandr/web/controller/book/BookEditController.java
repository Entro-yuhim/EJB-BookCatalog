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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
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
public class BookEditController extends AbstractBookModicicationController {
	private static final Logger LOG = LoggerFactory.getLogger(BookEditController.class);


	private Long id;

	public void init() {
		this.book = bookManager.getByIdWithInitializedCollections(id);
		authorModel = new AuthorAbstractLazyDataModel(this.book.getAuthors());
	}



	@Override
	public void save() {
		LOG.info("Updating Book with id [{}] with new data", book.getId());
		try {
			bookManager.update(book);
		} catch (ConstraintCheckException e) {
			// FIXME: 09.02.2016 add proper handling
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
