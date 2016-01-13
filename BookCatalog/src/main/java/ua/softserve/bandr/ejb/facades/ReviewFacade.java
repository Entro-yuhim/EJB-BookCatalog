package ua.softserve.bandr.ejb.facades;

import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ReviewFacade {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public List<Review> getAll(){
        return entityManager
                .createNamedQuery("Reviews.getAll", Review.class)
                .getResultList();
    }
}
