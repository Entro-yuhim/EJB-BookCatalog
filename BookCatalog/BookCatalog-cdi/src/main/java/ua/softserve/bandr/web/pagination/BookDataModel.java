package ua.softserve.bandr.web.pagination;

import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.BookDTO;
import ua.softserve.bandr.dto.converters.BookDTOTransformer;
import ua.softserve.bandr.dto.converters.DTOTransformer;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 28.01.2016.
 */
@Stateful
public class BookDataModel extends AbstractDTODataModel<Book, BookDTO>{

	private static final Logger LOG = LoggerFactory.getLogger(BookDataModel.class);
	private ArrangeableState state;

	@Inject
	private BookManager bookManager;

	private Map<Long, Boolean> checkedData = new HashMap<>();

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Book> getPersistablesList(Integer firstRow, Integer numRows) {
		invalidateCheckedMap();
		return bookManager.getPaged(firstRow, numRows);
	}

	private void invalidateCheckedMap() {
		checkedData = new HashMap<>();
	}

	@Override
	protected DTOTransformer<Book, BookDTO> getDTOTransformer() {
		return new BookDTOTransformer();
	}

	@Override
	public int getTotalCount() {
		return bookManager.getRecordCount();
	}

	public Map<Long, Boolean> getCheckedData() {
		return checkedData;
	}

	public void setCheckedData(Map<Long, Boolean> checkedData) {
		this.checkedData = checkedData;
	}

	@Override
	public void arrange(FacesContext context, ArrangeableState state) {
		LOG.info("Arranging");
		List stat = state.getFilterFields();
		this.state = state;
	}
}
