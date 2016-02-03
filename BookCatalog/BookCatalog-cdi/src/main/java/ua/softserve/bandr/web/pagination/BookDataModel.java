package ua.softserve.bandr.web.pagination;

import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.BookDTO;
import ua.softserve.bandr.dto.converters.BookDTOTransformer;
import ua.softserve.bandr.dto.converters.DTOTransformer;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.BookManager;
import ua.softserve.bandr.web.pagination.richmodels.AbstractDTODataModel;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 28.01.2016.
 */
@Stateful
public class BookDataModel extends AbstractDTODataModel<Book, BookDTO> {

	private static final Logger LOG = LoggerFactory.getLogger(BookDataModel.class);
	private ArrangeableState state;

	@Inject
	private BookManager bookManager;

	private Map<Long, Boolean> checkedData = new HashMap<>();

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Book> getPersistablesList(Integer firstRow, Integer numRows, Map<String, String> filter, Map<String, SortOrder> sortingMap) {
		//Hack to remove checked between pages. Should I change this to be a feature instead?
		invalidateCheckedMap();
		return bookManager.getPagedFiltered(firstRow, numRows, filter, convertSortingToOrder(sortingMap));
	}

	private void invalidateCheckedMap() {
		checkedData = new HashMap<>();
	}

	@Override
	protected DTOTransformer<Book, BookDTO> getDTOTransformer() {
		return new BookDTOTransformer();
	}

	@Override
	public int getTotalCount(Map<String, String> filter) {
		return bookManager.getRecordCount(filter);
	}

	public Map<Long, Boolean> getCheckedData() {
		return checkedData;
	}

	public void setCheckedData(Map<Long, Boolean> checkedData) {
		this.checkedData = checkedData;
	}
}
