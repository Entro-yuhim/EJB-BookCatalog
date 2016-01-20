package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//Should this be an actual Home JEE pattern?
@Stateless
public class AuthorHome {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public void saveAuthor(Author author){
        entityManager.persist(author);
    }

    public Author updateAuthor(Author author){
        return entityManager.merge(author);
    }

    public void deleteAuthor(Author author){
        entityManager.remove(entityManager.contains(author) ? author : entityManager.merge(author));
    }

}
