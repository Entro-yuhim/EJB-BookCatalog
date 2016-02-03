package ua.softserve.bandr.web.controller.book;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.web.controller.ContainsSotrableTable;
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



	public BookListController() {
		getSortOrders().put("title", SortOrder.unsorted);
		getSortOrders().put("rating", SortOrder.unsorted);
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

		//TODO using java8 for convenience.
//		checkedForAction.forEach((k, v) -> {
//			if (v) {
//				LOG.info("Checked id = [{}]", k);
//			}
//		});
		return "bookPage";
	}
}
