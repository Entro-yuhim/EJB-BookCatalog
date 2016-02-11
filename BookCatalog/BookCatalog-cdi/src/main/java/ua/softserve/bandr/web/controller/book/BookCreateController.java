package ua.softserve.bandr.web.controller.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Created by bandr on 03.02.2016.
 */
@ManagedBean
@ViewScoped
public class BookCreateController extends AbstractBookModicicationController {
	@Inject
	private BookManager bookManager;
	@Inject
	private AuthorManager authorManager;
	private static final Logger LOG = LoggerFactory.getLogger(BookCreateController.class);

	public void save() {
		LOG.info("Saving new instance of Book");
		try {
			bookManager.persist(book);
		} catch (ConstraintCheckException e) {
			LOG.warn("Collision attempting to persist book with ISBN [{}]", book.getiSBN());
			FacesContext.getCurrentInstance()
					.addMessage("bookData:isbn",
							new FacesMessage(String.format("Book with this ISBN %s already exists", book.getiSBN())));
		}
	}
}
