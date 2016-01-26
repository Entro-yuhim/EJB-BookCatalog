package ua.softserve.bandr.persistence.manager;

import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.home.AbstractHome;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bandr on 22.01.2016.
 */
public abstract class AbstractManager<T> {
    @PersistenceContext(name="pg_BC")
    protected EntityManager entityManager;
    protected abstract AbstractHome<T> getHome();
    protected abstract AbstractFacadeInt<T> getFacade();

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    protected void deleteBulk(List<T> entities){
        for (T entity : entities){
            getHome().delete(entity);
        }
    }

    public List<T> getAll(){
        return getFacade().getAll();
    }

    public T getById(Long id){
        return getFacade().getById(id);
    }

    public List<T> getPaged(Integer startWith, Integer pageSize){
        return getFacade().getPaged(startWith, pageSize);
    }

    public void persist(T entity) {
        getHome().persist(entity);
    }

}
