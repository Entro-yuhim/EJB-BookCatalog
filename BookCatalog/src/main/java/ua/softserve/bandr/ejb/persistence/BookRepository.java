package ua.softserve.bandr.ejb.persistence;

import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by bandr on 05.01.2016.
 */
@Stateless
public class BookRepository {
    @PersistenceContext(name="primary")
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveBook(Book book){
        System.out.println("got a book:");
        System.out.println(book);
        //entityManager.getTransaction().begin();
        entityManager.persist(book);
        //entityManager.getTransaction().commit();
    }
}
