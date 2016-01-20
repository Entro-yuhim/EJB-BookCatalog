package ua.softserve.bandr.persistence.manager;

import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class AuthorManager {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public void deleteBulk(List<Author> authors){
        for (Author a : authors){
            entityManager.remove(entityManager.contains(a) ? a : entityManager.merge(a));
        }
    }
}
