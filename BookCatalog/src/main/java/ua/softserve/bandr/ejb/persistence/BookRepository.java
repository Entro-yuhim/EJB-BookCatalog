package ua.softserve.bandr.ejb.persistence;

import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by bandr on 05.01.2016.
 */
@Stateless
public class BookRepository {
    private EntityManager entityManager;

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveBook(Book book){
        entityManager.persist(book);
    }

}
