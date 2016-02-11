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

	@Inject
	private BookManager bookManager;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Book> getPersistablesList(Integer firstRow, Integer numRows, Map<String, String> filter, Map<String, SortOrder> sortingMap) {
		return bookManager.getPagedFiltered(firstRow, numRows, filter, convertSortingToOrder(sortingMap));
	}

	@Override
	protected DTOTransformer<Book, BookDTO> getDTOTransformer() {
		return new BookDTOTransformer();
	}

	@Override
	public Long getTotalCount(Map<String, String> filter) {
		return bookManager.getRecordCount(filter);
	}
}
