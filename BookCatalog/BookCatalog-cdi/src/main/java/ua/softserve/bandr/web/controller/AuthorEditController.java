package ua.softserve.bandr.web.controller;

import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.manager.AuthorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 * Created by bandr on 02.02.2016.
 */
@ManagedBean
@RequestScoped
public class AuthorEditController {
	@Inject
	private AuthorManager authorManager;
	private Author author;
	@ManagedProperty(value = "#{authorId}")
	private Long id;

	@PostConstruct
	public void init(){
		author = authorManager.getById(id);
	}
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
}
