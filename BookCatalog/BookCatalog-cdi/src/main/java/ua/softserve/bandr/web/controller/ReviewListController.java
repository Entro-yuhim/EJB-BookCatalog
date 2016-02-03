package ua.softserve.bandr.web.controller;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.web.pagination.richmodels.JPADataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ManagedBean
@ViewScoped
//TODO: re-do this with DTODataModel
public class ReviewListController extends ContainsSotrableTable {
	private static final Logger LOG = LoggerFactory.getLogger(ReviewListController.class);

	@PersistenceContext(name = "pg_BC")
	private EntityManager entityManager;
	private JPADataModel<Review> model;

	public ReviewListController() {
		getSortOrders().put("username", SortOrder.unsorted);
	}

	public JPADataModel<Review> getModel() {
		LOG.info("Getting a model");
		return model;
	}

	@PostConstruct
	public void init() {
		this.model = new JPADataModel<Review>(entityManager, Review.class) {
			@Override
			protected Object getId(Review review) {
				return review.getId();
			}
		};
	}


}
