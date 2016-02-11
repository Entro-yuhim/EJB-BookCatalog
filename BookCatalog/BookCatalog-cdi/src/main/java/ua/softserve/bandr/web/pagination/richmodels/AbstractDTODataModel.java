package ua.softserve.bandr.web.pagination.richmodels;

import com.google.common.collect.Maps;
import org.richfaces.component.SortOrder;
import org.richfaces.model.Arrangeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.EntityDTO;
import ua.softserve.bandr.dto.converters.DTOTransformer;
import ua.softserve.bandr.entity.Persistable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 29.01.2016.
 */
public abstract class AbstractDTODataModel<U extends Persistable, T extends EntityDTO> extends AbstractLazyDataModel<T> implements Arrangeable {

	private static final Logger LOG = LoggerFactory.getLogger((AbstractDTODataModel.class));

	@Override
	public List<T> getDataList(int firstRow, int numRows, Map<String, String> filter, Map<String, SortOrder> sorting) {
		LOG.debug("Getting data list with first row [{}] and size [{}]. Filter [{}] and sorting [{}]", firstRow, numRows, filter, sorting);
		return getDTOTransformer().getDTOList(getPersistablesList(firstRow, numRows, filter, sorting));
	}


	protected abstract List<U> getPersistablesList(Integer firstRow, Integer numRows, Map<String, String> filter, Map<String, SortOrder> sortingMap);

	@Override
	public Object getKey(T t) {
		return t.getKey();
	}

	protected abstract DTOTransformer<U, T> getDTOTransformer();

	//I really don't know where to put this method.
	protected static Map<String, Boolean> convertSortingToOrder(Map<String, SortOrder> sortingMap) {
		Map<String, Boolean> result = Maps.newHashMap();
		for (Map.Entry<String, SortOrder> sorting : sortingMap.entrySet()) {
			result.put(sorting.getKey(), sorting.getValue() == SortOrder.ascending);
		}
		return result;
	}

}
