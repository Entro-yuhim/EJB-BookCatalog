package ua.softserve.bandr.web.controller;

import com.google.common.collect.Maps;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.web.pagination.JPADataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

/**
 * Created by bandr on 29.01.2016.
 */
@ManagedBean
@ViewScoped
public class ReviewListController extends ContainsSotrableTable {
	private static final Logger LOG = LoggerFactory.getLogger(ReviewListController.class);

	@PersistenceContext(name = "pg_BC")
	private EntityManager entityManager;
	private String sortProperty;
	private JPADataModel<Review> model;
	private Map<String, String> filterValues = Maps.newHashMap();
	private Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(1);

	public ReviewListController() {
		sortOrders.put("username", SortOrder.unsorted);
	}

	@Override
	public Map<String, SortOrder> getSortOrders() {
		return sortOrders;
	}

	@Override
	public void setSortOrders(Map<String, SortOrder> sortOrders) {
		this.sortOrders = sortOrders;
	}

	public Map<String, String> getFilterValues() {
		return filterValues;
	}

	public void setFilterValues(Map<String, String> filterValues) {
		this.filterValues = filterValues;
	}

	public JPADataModel<Review> getModel() {
		LOG.info("Getting a model");
		return model;
	}

	@Override
	public String getSortProperty() {
		return sortProperty;
	}

	@Override
	public void setSortProperty(String sortPropety) {
		this.sortProperty = sortPropety;
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
