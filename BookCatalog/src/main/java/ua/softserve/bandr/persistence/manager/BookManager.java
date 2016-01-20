package ua.softserve.bandr.persistence.manager;

import ua.softserve.bandr.entity.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class BookManager {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

        public void deleteBulk(List<Book> books) {
            for (Book b : books)
                entityManager.remove(entityManager.contains(b) ? b : entityManager.merge(b));
    }
}
