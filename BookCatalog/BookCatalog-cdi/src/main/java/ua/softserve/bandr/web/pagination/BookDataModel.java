package ua.softserve.bandr.web.pagination;

import ua.softserve.bandr.entity.Book;

import java.util.List;

/**
 * Created by bandr on 28.01.2016.
 */
public class BookDataModel extends AbstractLazyDataModel<Book> {
    @Override
    public List<Book> getDataList(int firstRow, int numRows) {
        return null;
    }

    @Override
    public Object getKey(Book book) {
        return null;
    }

    @Override
    public int getTotalCount() {
        return 0;
    }
}
