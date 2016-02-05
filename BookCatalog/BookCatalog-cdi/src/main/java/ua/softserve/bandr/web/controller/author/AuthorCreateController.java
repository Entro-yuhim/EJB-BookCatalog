package ua.softserve.bandr.web.controller.author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.manager.AuthorManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

/**
 * Created by bandr on 05.02.2016.
 */
@ManagedBean
@RequestScoped
public class AuthorCreateController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthorCreateController.class);
	@Inject
	private AuthorManager authorManager;

	public AuthorCreateController() {
		LOG.info("Creating new AuthorCreateController");
	}

	@AssertTrue(message = "Author already exists in database.")
	public boolean isAuthorUnique() {
		return !authorManager.isAuthorUnique(author);
	}

	private Author author = new Author();

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void save() {
		LOG.info("Saving");
		author = new Author();
	}
}
