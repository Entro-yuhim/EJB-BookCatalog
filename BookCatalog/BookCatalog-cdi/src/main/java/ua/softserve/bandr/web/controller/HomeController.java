package ua.softserve.bandr.web.controller;

import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by bandr on 27.01.2016.
 */
@ManagedBean
@RequestScoped
public class HomeController {
	@Inject
	private BookManager bookManager;

	public List<BookRatingDTO> getBookData() {
		return bookManager.getBookRatingData();
	}

}
