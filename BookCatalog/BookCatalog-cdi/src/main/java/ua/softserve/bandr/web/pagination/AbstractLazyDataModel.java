package ua.softserve.bandr.web.pagination;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 28.01.2016.
 * <p>
 * credit to: http://pastebin.com/raw/evUwT8VY
 */
public abstract class AbstractLazyDataModel<T> extends ExtendedDataModel<T> {
    private Integer cachedRowCount;
    private SequenceRange cachedRange;
    private List<T> cachedList;
    private Map<Object, T> cachedMap = new HashMap<Object, T>();
    private Object rowKey;

    public abstract List<T> getDataList(int firstRow, int numRows);

    public abstract Object getKey(T t);

    public abstract int getTotalCount();


    @Override
    public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
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
            list = getDataList(sr.getFirstRow(), sr.getRows());
            cachedList = list;
        }
        return list;
    }


    @Override
    public boolean isRowAvailable() {
        return false;
    }

    @Override
    public int getRowCount() {
        if (cachedRowCount == null) {
            cachedRowCount = getTotalCount();
        }
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
    public int getRowIndex() {
        throw new UnsupportedOperationException("Something is really wrong");
    }

    @Override
    public void setRowIndex(int rowIndex) {
        throw new UnsupportedOperationException("Something is really wrong");
    }

    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException("Something is really wrong");
    }

    @Override
    public void setWrappedData(Object data) {
        throw new UnsupportedOperationException("Something is really wrong");
    }
}
