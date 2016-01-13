package ua.softserve.bandr.ejb.facades;

import com.google.common.base.Optional;
import org.hibernate.cfg.NotYetImplementedException;
import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AuthorFacade {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public List<Author> getAll(){
        return entityManager
                .createNamedQuery("Author.getAll", Author.class)
                .getResultList();
    }

    public List<Author> getByLastName(String lastName) {
        return entityManager
                .createNamedQuery("Author.getByLastName", Author.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public List<Author> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                               Optional<String> filterText, Optional<Author.AuthorSorting> sorting){
        throw new NotYetImplementedException();
    }

}
