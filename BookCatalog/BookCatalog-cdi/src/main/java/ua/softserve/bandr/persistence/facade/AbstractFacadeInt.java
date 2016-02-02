package ua.softserve.bandr.persistence.facade;

import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Persistable;

import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 22.01.2016.
 */
public interface AbstractFacadeInt<T extends Persistable> {

	List<T> getAll();

	T getById(Long id);

	List<T> getPaged(Integer startWith, Integer pageSize);

	List<T> getPagedFilteredSorted(Integer startWith, Integer pageSize, Map<String, String> filterText, Map<String, Boolean> sortingOrder);

	Integer getRecordCount(Map<String, String> filter);
}
