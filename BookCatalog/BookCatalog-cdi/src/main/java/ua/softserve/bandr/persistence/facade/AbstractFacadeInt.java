package ua.softserve.bandr.persistence.facade;

import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
public interface AbstractFacadeInt<T> {

    List<T> getAll();

    T getById(Long id);

    List<T> getPaged(Integer startWith, Integer pageSize);
}
