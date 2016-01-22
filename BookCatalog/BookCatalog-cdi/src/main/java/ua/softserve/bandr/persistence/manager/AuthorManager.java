package ua.softserve.bandr.persistence.manager;

import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.AuthorFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.AuthorHome;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class AuthorManager extends AbstractManager<Author> {

    @Inject
    private AuthorHome authorHome;
    @Inject
    private AuthorFacade authorFacade;
    @Resource
    EJBContext ejbContext;

    @Override
    protected AbstractHome<Author> getHome() {
        return authorHome;
    }

    @Override
    protected AbstractFacadeInt<Author> getFacade() {
        return authorFacade;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Author author) throws AuthorConstraintException {
        Author authorDB = authorHome.update(author);
        if (!authorDB.getBooks().isEmpty()) {
            ejbContext.setRollbackOnly();
            throw new AuthorConstraintException();
        }
        authorHome.delete(authorDB);
    }

    @Override
    public void deleteBulk(List<Author> authors) {
        try {
            // TODO: 22.01.2016 re-think this method signature.
            for (Author author : authors) {
                delete(author);
            }
        } catch (AuthorConstraintException e) {
            e.printStackTrace();
            ejbContext.setRollbackOnly();
        }
    }
}