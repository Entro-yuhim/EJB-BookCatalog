package ua.softserve.bandr.web.controller.author;

import org.richfaces.component.SortOrder;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.web.pagination.richmodels.AbstractLazyDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bandr on 09.02.2016.
 */
final class BookAbstractLazyDataModel extends AbstractLazyDataModel<Book> {

	private List<Book> books = new ArrayList<>();

	BookAbstractLazyDataModel(Set<Book> books) {
		this.books = new ArrayList<>(books);
	}

	BookAbstractLazyDataModel() {
	}

	@Override
	public List<Book> getDataList(int firstRow, int numRows, Map<String, String> filter, Map<String, SortOrder> sorting) {
		return books.subList(firstRow, (firstRow + numRows) < books.size() ? (firstRow + numRows) : books.size());
	}

	@Override
	public Object getKey(Book book) {
		return book.getId();
	}

	@Override
	public Long getTotalCount(Map<String, String> filter) {
		return (long) books.size();
	}

}
