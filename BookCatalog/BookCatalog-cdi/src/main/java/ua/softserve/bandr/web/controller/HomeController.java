package ua.softserve.bandr.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.BookRatingDTO;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.ws.client.ReviewClient;

import javax.annotation.PostConstruct;
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
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	@Inject
	private BookManager bookManager;
	@Inject
	private ReviewClient reviewClient;

//	@PostConstruct
//	public void doStuff() {
//		Review reviewById = reviewClient.getReviewById(10L);
//		LOG.info("Doing stuff");
//	}

	public List<BookRatingDTO> getBookData() {
		return bookManager.getBookRatingData();
	}

}
