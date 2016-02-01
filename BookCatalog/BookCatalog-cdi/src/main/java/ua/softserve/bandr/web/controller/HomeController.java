package ua.softserve.bandr.web.controller;

import com.google.common.collect.Lists;
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
		bookManager.getPagedFiltered(1, 1, Lists.newArrayList(), Lists.newArrayList());
		return bookManager.getBookRatingData();
	}

}
