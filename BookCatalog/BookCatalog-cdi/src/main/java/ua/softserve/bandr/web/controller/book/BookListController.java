package ua.softserve.bandr.web.controller.book;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.BookDTO;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.web.controller.ContainsSortableTable;
import ua.softserve.bandr.web.pagination.BookDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by bandr on 29.01.2016.
 */
@ManagedBean
@ViewScoped
public class BookListController extends ContainsSortableTable {
	private static final Logger LOG = LoggerFactory.getLogger(BookListController.class);

	@Inject
	private BookDataModel model;
	@Inject
	private BookManager bookManager;
	private Set<Long> selection = Sets.newHashSet();
	private String ratingFilter;

	public BookListController() {
		getSortOrders().put("id", SortOrder.unsorted);
		getSortOrders().put("title", SortOrder.unsorted);
		getSortOrders().put("rating", SortOrder.ascending);
	}

	public void init() {
		if (ratingFilter != null) {
			HashMap<String, String> cachedFilter = Maps.newHashMap();
			cachedFilter.put("rating", ratingFilter);
			model.setCachedFilter(cachedFilter);
		}
	}

	public BookDataModel getBooks() {
		return model;
	}

	public String deleteSelected() {
		LOG.info("Delete Selected");
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

	public void setSelectedValues(Collection values) {
	}


	/**
	 * User cannot delete values that are not currently being rendered.
	 *
	 * @return Collection of bookDTOs that are selected by user and are present in data model.
	 */
	public Collection<BookDTO> getSelectedValues() {
		return Collections2.filter(model.getCachedList(), new BookDTOPredicate());
	}

	public void doNothing() {
		LOG.info("doNothing");
	}


	public String getRatingFilter() {
		return ratingFilter;
	}

	public void setRatingFilter(String ratingFilter) {
		this.ratingFilter = ratingFilter;
	}

	private class BookDTOPredicate implements Predicate<BookDTO> {
		@Override
		public boolean apply(BookDTO input) {
			return selection.contains(input.getId());
		}
	}
}
