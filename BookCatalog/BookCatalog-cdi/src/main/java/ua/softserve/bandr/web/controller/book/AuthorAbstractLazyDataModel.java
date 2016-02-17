package ua.softserve.bandr.web.controller.book;

import org.richfaces.component.SortOrder;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.web.pagination.richmodels.AbstractLazyDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bandr on 09.02.2016.
 */
final class AuthorAbstractLazyDataModel extends AbstractLazyDataModel<Author> {

	private List<Author> authors = new ArrayList<>();

	AuthorAbstractLazyDataModel(Set<Author> authors) {
		this.authors = new ArrayList<>(authors);
	}

	AuthorAbstractLazyDataModel() {
	}

	@Override
	public List<Author> getDataList(int firstRow, int numRows, Map<String, String> filter, Map<String, SortOrder> sorting) {
		return authors.subList(firstRow, (firstRow + numRows) < authors.size() ? (firstRow + numRows) : authors.size());
	}

	@Override
	public Object getKey(Author author) {
		return author == null ? 0 : author.getId();
	}

	@Override
	public Long getTotalCount(Map<String, String> filter) {
		return (long) authors.size();
	}

}