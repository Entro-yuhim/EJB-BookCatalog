package ua.softserve.bandr.web.controller.book;

import com.google.common.collect.Lists;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.web.controller.ContainsSotrableTable;
import ua.softserve.bandr.web.pagination.BookDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
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
	@Inject
	private BookManager bookManager;
	//TODO: change to extendedDataTable
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
		List<Long> listForAction = Lists.newArrayList();
		for (Map.Entry<Long, Boolean> entry : checkedForAction.entrySet()) {
			if (entry.getValue() && entry.getValue() != null) {
				listForAction.add(entry.getKey());
			}
			bookManager.deleteBulkById(listForAction);
		}

		return "bookPage";
	}
}
