package ua.softserve.bandr.web.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.EntityDTO;
import ua.softserve.bandr.dto.converters.DTOTransformer;
import ua.softserve.bandr.entity.Persistable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

/**
 * Created by bandr on 29.01.2016.
 */
public abstract class AbstractDTODataModel<U extends Persistable, T extends EntityDTO> extends AbstractLazyDataModel<T> {

	private static final Logger LOG = LoggerFactory.getLogger((AbstractDTODataModel.class));

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<T> getDataList(int firstRow, int numRows) {
		LOG.info("Getting data list with first row [{}] and size [{}]", firstRow, numRows);
		return getDTOTransformer().getDTOList(getPersistablesList(firstRow, numRows));
	}

	protected abstract List<U> getPersistablesList(Integer firstRow, Integer numRows);

	@Override
	public Object getKey(T t) {
		return t.getKey();
	}

	protected abstract DTOTransformer<U, T> getDTOTransformer();
}
