package ua.softserve.bandr.persistence.facade;

import com.google.common.base.Optional;
import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ReviewFacade {
    @PersistenceContext(name = "pg_BC")
    private EntityManager entityManager;
    @Inject
    private PersistenceManager persistenceManager;

    public List<Review> getAll() {
        return persistenceManager.executeQuery(entityManager.createNamedQuery("Reviews.getAll", Review.class));
    }

    public Review getById(Long id) {
        return entityManager.find(Review.class, id);
    }

    public List<Review> getPaged(Integer startWith, Integer pageSize){
        return persistenceManager.executeQuery(entityManager.createNamedQuery("Reviews.getAll", Review.class), Optional.of(startWith), Optional.of(pageSize));
    }
    //TODO: add sorting. I really wish I could see RF API at this point.
}
