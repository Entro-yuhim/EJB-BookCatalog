package ua.softserve.bandr.web.pagination;

import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by bandr on 28.01.2016.
 */
@Stateful
public class BookDataModel extends AbstractLazyDataModel<Book> {
    @Inject
    private BookManager bookManager;

    @Override
    public List<Book> getDataList(int firstRow, int numRows) {
        return bookManager.getPaged(firstRow, numRows);
    }

    @Override
    public Object getKey(Book book) {
        return null;
    }

    @Override
    public int getTotalCount() {
        return bookManager.getRecordCount();
    }
}
