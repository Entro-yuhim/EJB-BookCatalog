package ua.softserve.bandr.web.controller.book;

import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

/**
 * Created by bandr on 01.02.2016.
 */
@ManagedBean
@RequestScoped
public class BookEditController {
	@ManagedProperty(value = "#{param.bookId}")
	private Integer bookId;
	@Inject
	private BookManager bookManager;

	private Book book;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@PostConstruct
	private void init(){
		book = bookManager.getById(Long.valueOf(bookId));
	}
}
