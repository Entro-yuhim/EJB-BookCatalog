package ua.softserve.bandr.persistance.facades;

import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ReviewFacade {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;
    @Inject
    private QueryManager queryManager;

    public List<Review> getAll(){
        return queryManager.executeQuery(entityManager.createNamedQuery("Reviews.getAll", Review.class));
    }
}
