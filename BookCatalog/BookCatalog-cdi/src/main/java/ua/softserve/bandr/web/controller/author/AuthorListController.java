package ua.softserve.bandr.web.controller.author;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.AuthorDTO;
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
	private Collection<AuthorDTO> canBeDeleted = Sets.newHashSet();
	private Collection<AuthorDTO> cannotBeDeleted = Sets.newHashSet();

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
		authorManager.deleteAllById(Collections2.transform(canBeDeleted, new Function<AuthorDTO, Long>() {
			@Override
			public Long apply(AuthorDTO input) {
				return input.getId();
			}
		}));
	}

	public Set<Long> getSelection() {
		return selection;
	}

	public void setSelection(Set<Long> collection) {
		this.selection = collection;
	}

	public Collection getCanBeDeleted() {
		return canBeDeleted;
	}

	public Collection getCannotBeDeleted() {
		return cannotBeDeleted;
	}

	public void setCanBeDeleted(Collection<AuthorDTO> canBeDeleted) {
		this.canBeDeleted = canBeDeleted;
	}

	public void setCannotBeDeleted(Collection<AuthorDTO> cannotBeDeleted) {
		this.cannotBeDeleted = cannotBeDeleted;
	}

	public void doNothing() {
		canBeDeleted = Sets.newHashSet();
		cannotBeDeleted = Sets.newHashSet();
		Collection<AuthorDTO> selectedList = Collections2.filter(authors.getCachedList(), new Predicate<AuthorDTO>() {
			@Override
			public boolean apply(AuthorDTO input) {
				return selection.contains(input.getId());
			}
		});
		for (AuthorDTO authorDTO : selectedList) {
			if (authorManager.canBeDeleted(authorDTO.getId())) {
				canBeDeleted.add(authorDTO);
			} else {
				cannotBeDeleted.add(authorDTO);
			}
		}
	}
}
