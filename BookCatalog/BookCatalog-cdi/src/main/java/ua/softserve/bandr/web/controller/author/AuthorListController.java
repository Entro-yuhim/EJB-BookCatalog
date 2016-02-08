package ua.softserve.bandr.web.controller.author;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.web.controller.ContainsSotrableTable;
import ua.softserve.bandr.web.pagination.AuthorDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

/**
 * Created by bandr on 02.02.2016.
 */
@ManagedBean
@ViewScoped
public class AuthorListController extends ContainsSotrableTable {

	private static final Logger LOG = LoggerFactory.getLogger(AuthorListController.class);
	private Set<Long> selection;

	public AuthorListController() {
		getSortOrders().put("id", SortOrder.unsorted);
		getSortOrders().put("firstName", SortOrder.unsorted);
		getSortOrders().put("lastName", SortOrder.unsorted);
		getSortOrders().put("rating", SortOrder.unsorted);
	}

	@Inject
	private AuthorDataModel authors;
	@Inject
	private AuthorManager authorManager;

	public AuthorDataModel getAuthors() {
		return authors;
	}

	public void setAuthors(AuthorDataModel authors) {
		this.authors = authors;
	}

	public void deleteSelected() {
		LOG.info("asdfasfdas");
		authorManager.deleteAllById(selection);
	}

	public Set<Long> getSelection() {
		return selection;
	}
	public void setSelection(Set<Long> collection) {
		this.selection = collection;
	}
}
