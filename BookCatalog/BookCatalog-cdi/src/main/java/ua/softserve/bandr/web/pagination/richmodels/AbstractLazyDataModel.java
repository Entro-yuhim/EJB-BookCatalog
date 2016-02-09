package ua.softserve.bandr.web.pagination.richmodels;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.apache.commons.lang3.StringUtils;
import org.richfaces.component.SortOrder;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.FilterField;
import org.richfaces.model.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 28.01.2016.
 */
public abstract class AbstractLazyDataModel<T> extends ExtendedDataModel<T> implements Arrangeable {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractLazyDataModel.class);
	private Integer cachedRowCount;
	private SequenceRange cachedRange;
	private List<T> cachedList = Lists.newArrayList();
	private final Map<Object, T> cachedMap = Maps.newHashMap();
	private Object rowKey;
	private Map<String, String> cachedFilter = Maps.newHashMap();
	private Map<String, SortOrder> cachedSorting = Maps.newHashMap();

	public abstract List<T> getDataList(int firstRow, int numRows, Map<String, String> filter, Map<String, SortOrder> sorting);

	public abstract Object getKey(T t);

	public abstract Long getTotalCount(Map<String, String> filter);

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
		LOG.debug("Walk");
		SequenceRange sr = (SequenceRange) range;
		List<T> list = getList(sr);
		for (T t : list) {
			Object key = getKey(t);
			if (key == null) {
				throw new IllegalStateException("Found null key");
			}
			cachedMap.put(key, t);
			visitor.process(context, key, argument);
		}
	}

	private List<T> getList(SequenceRange sr) {
		LOG.info("getList");
		List<T> list;
		if (cachedRange != null && sr.getFirstRow() == cachedRange.getFirstRow() && sr.getRows() == cachedRange.getRows()) {
			list = cachedList;
		} else {
			cachedRange = sr;
			list = getDataList(sr.getFirstRow(), sr.getRows(), cachedFilter, cachedSorting);
			cachedList = list;
		}
		return list;
	}

	protected void invalidateCache() {
		/* TODO check how to properly invalidate this cache.
		* Is it even necessary?
		* */

		cachedRange = null;
		cachedRowCount = null;
		cachedList = Lists.newArrayList();
		cachedMap.clear();
	}

	private static Map<String, String> buildFilterFromState(ArrangeableState arrangeableState) {
		Map<String, String> result = Maps.newHashMap();
		if (arrangeableState == null) {
			return result;
		}
		for (FilterField filterField : arrangeableState.getFilterFields()) {
			String key = (String) filterField.getFilterExpression().getValue(FacesContext.getCurrentInstance().getELContext());
			String value = (String) filterField.getFilterValue();
			if (!StringUtils.isEmpty(value)) {
				result.put(key, value);
			}
		}
		return result;
	}

	@Override
	public boolean isRowAvailable() {
		return !cachedList.isEmpty();
	}

	@Override
	public int getRowCount() {
		if (cachedRowCount == null) {
			cachedRowCount = getTotalCount(cachedFilter).intValue();
		}
		LOG.debug("Row Count with current filter [{}] is [{}]", cachedFilter, cachedRowCount);
		return cachedRowCount;
	}

	@Override
	public T getRowData() {
		return cachedMap.get(getRowKey());
	}

	@Override
	public void setRowKey(Object key) {
		LOG.debug("Set rowKey [{}]", key);
		this.rowKey = key;
	}

	@Override
	public Object getRowKey() {
		return this.rowKey;
	}

	@Override
	public void arrange(FacesContext context, ArrangeableState state) {
		Map<String, SortOrder> sortingMap = buildSortingFromState(state);
		Map<String, String> newFilter = buildFilterFromState(state);
		if (!newFilter.equals(cachedFilter) || !sortingMap.equals(cachedSorting)) {
			invalidateCache();
			cachedFilter = newFilter;
			cachedSorting = sortingMap;
		}
		LOG.debug("Current filter [{}], current sorting [{}]", cachedFilter, cachedSorting);
	}

	private static Map<String, SortOrder> buildSortingFromState(ArrangeableState state) {
		Map<String, SortOrder> map = Maps.newHashMap();
		if (state == null) {
			return map;
		}
		List<SortField> sortFields = state.getSortFields();
		if (sortFields == null || sortFields.isEmpty()) {
			return map;
		}
		for (SortField sortField : sortFields) {
			String o = (String) sortField.getSortBy().getValue(FacesContext.getCurrentInstance().getELContext());
			map.put(o, sortField.getSortOrder());
		}
		return map;
	}

	@Override
	public int getRowIndex() {
		LOG.info("Attempting to get Row Index");
		return -1;
	}

	@Override
	public void setRowIndex(int rowIndex) {
		LOG.info("Attempting to set Row Index");
	}

	@Override
	public Object getWrappedData() {
		LOG.info("Attempting to get wrapped data");
		return null;
	}

	@Override
	public void setWrappedData(Object data) {
		LOG.info("Attempting to set wrapped data");
	}
}
