package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateful
public class ReviewHome {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public void persistReview(Review review){
        entityManager.persist(review);
    }

    public Review updateReview(Review review){
        return entityManager.merge(review);
    }

    public void removeReview(Review review){
        entityManager.remove(entityManager.contains(review) ? review : entityManager.merge(review));
    }
}
