package ua.softserve.bandr.web.controller.author;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.web.controller.ContainsSotrableTable;
import ua.softserve.bandr.web.pagination.AuthorDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by bandr on 02.02.2016.
 */
@ManagedBean
@ViewScoped
public class AuthorListController extends ContainsSotrableTable {

	private static final Logger LOG = LoggerFactory.getLogger(AuthorListController.class);
	private Collection selection;

	public AuthorListController() {
		getSortOrders().put("id", SortOrder.unsorted);
		getSortOrders().put("firstName", SortOrder.unsorted);
		getSortOrders().put("lastName", SortOrder.unsorted);
		getSortOrders().put("rating", SortOrder.unsorted);
	}

	@Inject
	private AuthorDataModel authors;

	public AuthorDataModel getAuthors() {
		return authors;
	}

	public void setAuthors(AuthorDataModel authors) {
		this.authors = authors;
	}

	public void doStuff() {
		LOG.info("asdfasfdas");
	}

	public Collection getSelection() {
		return selection;
	}
	public void setSelection(Collection collection) {
		this.selection = collection;
	}
}
