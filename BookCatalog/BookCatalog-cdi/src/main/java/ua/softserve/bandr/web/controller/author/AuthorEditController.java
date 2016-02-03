package ua.softserve.bandr.web.controller.author;

import com.google.common.collect.Lists;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by bandr on 02.02.2016.
 */
@ManagedBean
@RequestScoped
public class AuthorEditController {
	@Inject
	private AuthorManager authorManager;
	@Inject
	private BookManager bookManager;

	@ManagedProperty(value = "#{param.authorId}")
	private Long id;
	private Author author;
	private List<Book> books = Lists.newArrayList();

	@PostConstruct
	public void init() {
		author = authorManager.getById(id);
		books = bookManager.getByAuthorId(author.getId());
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
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
}
