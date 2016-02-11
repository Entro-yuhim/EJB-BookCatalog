package ua.softserve.bandr.web.controller.author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.AssertTrue;
import java.io.IOException;

/**
 * Created by bandr on 05.02.2016.
 */
@ManagedBean
@RequestScoped
public class AuthorCreateController extends AbstractAuthorModificationController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthorCreateController.class);

	public AuthorCreateController() {
		LOG.info("Creating new AuthorCreateController");
	}

	@Override
	public void save() throws IOException {
		try {
			authorManager.persist(author);
			FacesContext.getCurrentInstance().getExternalContext().redirect("authorPage.jsf");
		} catch (ConstraintCheckException e) {
			FacesContext.getCurrentInstance()
					.addMessage("authorData:ajaxValidation", new FacesMessage("Author with this first name and last name are already in database"));
		}
	}
}
