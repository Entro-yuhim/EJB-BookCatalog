package ua.softserve.bandr.web.controller;

import org.richfaces.component.SortOrder;
import ua.softserve.bandr.web.pagination.AuthorDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Created by bandr on 02.02.2016.
 */
@ManagedBean
@ViewScoped
public class AuthorListController extends ContainsSotrableTable {

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
}
