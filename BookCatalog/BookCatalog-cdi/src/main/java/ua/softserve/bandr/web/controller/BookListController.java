package ua.softserve.bandr.web.controller;

import com.google.common.collect.Maps;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.web.pagination.BookDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bandr on 29.01.2016.
 */
@ManagedBean
@ViewScoped
public class BookListController extends ContainsSotrableTable {
	private static final Logger LOG = LoggerFactory.getLogger(BookListController.class);

	@Inject
	private BookDataModel model;
	private Map<Long, Boolean> checkedForAction = new HashMap<>();

	private Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(2);
	private String sortProperty;
	private Map<String, String> filterValues = Maps.newHashMap();

	public BookListController() {
		sortOrders.put("title", SortOrder.unsorted);
		sortOrders.put("rating", SortOrder.unsorted);
	}

	public Map<Long, Boolean> getCheckedForAction() {
		return checkedForAction;
	}

	public void setCheckedForAction(Map<Long, Boolean> checkedForAction) {
		this.checkedForAction = checkedForAction;
	}

	public BookDataModel getBooks() {
		checkedForAction = model.getCheckedData();
		return model;
	}

	public String doStuff() {
		LOG.info("Doing stuffz");

		//TODO remove. this is just a debug output of values in map. Using java8 for convenience.
//		checkedForAction.forEach((k, v) -> {
//			if (v) {
//				LOG.info("Checked id = [{}]", k);
//			}
//		});
		return "bookPage";
	}

	public Map<String, String> getFilterValues() {
		//LOG.info("Current filter: [{}]", filterValues);
		return filterValues;
	}

	public void setFilterValues(Map<String, String> filterValues) {
		this.filterValues = filterValues;
	}

	@Override
	public Map<String, SortOrder> getSortOrders() {
		return sortOrders;
	}

	@Override
	public void setSortOrders(Map<String, SortOrder> sortOrders) {
		this.sortOrders = sortOrders;
	}

	@Override
	public String getSortProperty() {
		return sortProperty;
	}

	@Override
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}
}
