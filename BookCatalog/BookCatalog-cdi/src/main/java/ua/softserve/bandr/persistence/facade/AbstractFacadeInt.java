package ua.softserve.bandr.persistence.facade;

import ua.softserve.bandr.entity.Persistable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
public interface AbstractFacadeInt<T extends Persistable> {

	List<T> getAll();

	T getById(Long id);
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	List<T> getPaged(Integer startWith, Integer pageSize);

	Integer getRecordCount();
}
