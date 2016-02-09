package ua.softserve.bandr.web.controller.author;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
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
import java.util.List;
import java.util.Set;

/**
 * Created by bandr on 02.02.2016.
 */
@ManagedBean
@ViewScoped
public class AuthorEditController extends AbstractAuthorModificationController {
	private Long id;

	public void init() {
		author = authorManager.getByIdWithInitializedCollections(id);
		bookModel = new BookAbstractLazyDataModel(author.getBooks());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void save() {
		try {
			authorManager.update(author);
		} catch (ConstraintCheckException e) {
			FacesContext.getCurrentInstance().addMessage("authorData:ajaxValidation", new FacesMessage("Author with this first name and last name are already in database"));
		}
	}

}