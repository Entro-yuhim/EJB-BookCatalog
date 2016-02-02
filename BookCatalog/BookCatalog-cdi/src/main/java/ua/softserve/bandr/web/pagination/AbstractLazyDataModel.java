package ua.softserve.bandr.web.pagination;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 28.01.2016.
 * <p/>
 * credit to: http://pastebin.com/raw/evUwT8VY
 */
public abstract class AbstractLazyDataModel<T> extends ExtendedDataModel<T> implements Arrangeable {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractLazyDataModel.class);
	private Integer cachedRowCount;
	private SequenceRange cachedRange;
	private List<T> cachedList = new ArrayList<>();
	private final Map<Object, T> cachedMap = new HashMap<>();
	private Object rowKey;
	private ArrangeableState arrangeableState;
	private Map<String, String> cachedFilter = Maps.newHashMap();

	public abstract List<T> getDataList(int firstRow, int numRows, Map<String, String> filter);

	public abstract Object getKey(T t);

	public abstract int getTotalCount(Map<String, String> filter);

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
		LOG.info("Walk");
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
		List<T> list;
		if (cachedRange != null && sr.getFirstRow() == cachedRange.getFirstRow() && sr.getRows() == cachedRange.getRows()) {
			list = cachedList;
		} else {
			cachedRange = sr;
			list = getDataList(sr.getFirstRow(), sr.getRows(), cachedFilter);
			cachedList = list;
		}
		return list;
	}

	protected void invalidateCache() {
		cachedRange = null;
		cachedRowCount = null;
		cachedList = Lists.newArrayList();
		cachedMap.clear();
	}

	private static Map<String, String> buildFilterFromState(ArrangeableState arrangeableState) {
		Map<String, String> result = new HashMap<>();
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
			cachedRowCount = getTotalCount(cachedFilter);
		}
		//LOG.info("Row Count with current filter [{}] is [{}]", cachedFilter, cachedRowCount);
		return cachedRowCount;
	}

	@Override
	public T getRowData() {
		return cachedMap.get(getRowKey());
	}

	@Override
	public void setRowKey(Object key) {
		this.rowKey = key;
	}

	@Override
	public Object getRowKey() {
		return this.rowKey;
	}

	@Override
	public void arrange(FacesContext context, ArrangeableState state) {
		Map sortingMap = buildSortingFromState(state);
		LOG.info("oldFilter [{}]", cachedFilter);
		Map<String, String> newFilter = buildFilterFromState(state);
		LOG.info("newFilter [{}]", newFilter);
		//Invalidate cache if state contains new filter
		if (!newFilter.equals(cachedFilter)) {
			invalidateCache();
			cachedFilter = newFilter;
		}
		this.arrangeableState = state;
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
		for (SortField sortField : sortFields){
			String o = (String) sortField.getSortBy().getValue(FacesContext.getCurrentInstance().getELContext());
			sortField.getSortOrder();
		}

		return map;
	}

	@Override
	public int getRowIndex() {
		throw new UnsupportedOperationException("DataModel is using trying to use unimplemented method.");
	}

	@Override
	public void setRowIndex(int rowIndex) {
		throw new UnsupportedOperationException("DataModel is using trying to use unimplemented method.");
	}

	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException("DataModel is using trying to use unimplemented method.");
	}

	@Override
	public void setWrappedData(Object data) {
		throw new UnsupportedOperationException("DataModel is using trying to use unimplemented method.");
	}
}
