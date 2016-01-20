package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class BookHome {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;
    public void saveBook(Book book){
        entityManager.persist(book);
    }

    public Book updateBook(Book book){
        return entityManager.merge(book);
    }

    public void deleteBook(Book book){
        entityManager.remove(entityManager.contains(book) ? book : entityManager.merge(book));
    }
}
