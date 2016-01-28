package ua.softserve.bandr.persistence.facade;

import ua.softserve.bandr.entity.Persistable;

import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
public interface AbstractFacadeInt<T extends Persistable> {

    List<T> getAll();

    T getById(Long id);

    List<T> getPaged(Integer startWith, Integer pageSize);

    Integer getRecordCount();
}
