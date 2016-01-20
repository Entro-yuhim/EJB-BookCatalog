package ua.softserve.bandr.persistence.manager;

import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class ReviewManager {
    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public void deleteBulk(List<Review> reviewList){
        for (Review review : reviewList)
            entityManager.remove(entityManager.contains(review) ? review : entityManager.merge(review));
    }
}
