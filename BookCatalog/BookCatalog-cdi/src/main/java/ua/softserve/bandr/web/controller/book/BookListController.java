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
import java.util.List;
import java.util.Set;

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
	private Set<Long> selection;

	public BookListController() {
		getSortOrders().put("id", SortOrder.unsorted);
		getSortOrders().put("title", SortOrder.unsorted);
		getSortOrders().put("rating", SortOrder.unsorted);
	}


	public BookDataModel getBooks() {
		return model;
	}

	public String deleteSelected() {
		List<Long> listForAction = Lists.newArrayList(selection);
		bookManager.deleteBulkById(listForAction);
		return "bookPage";
	}

	public Set<Long> getSelection() {
		return selection;
	}

	public void setSelection(Set<Long> selection) {
		this.selection = selection;
	}
}
