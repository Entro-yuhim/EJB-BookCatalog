package ua.softserve.bandr.persistence.manager;

import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.AbstractFacadeInt;
import ua.softserve.bandr.persistence.facade.ReviewFacade;
import ua.softserve.bandr.persistence.home.AbstractHome;
import ua.softserve.bandr.persistence.home.ReviewHome;
import ua.softserve.bandr.validation.EntityValidator;
import ua.softserve.bandr.validation.ValidationResult;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by bandr on 20.01.2016.
 */
@Stateless
public class ReviewManager extends AbstractManager<Review> {
    @Inject
    private ReviewHome reviewHome;
    @Inject
    private ReviewFacade reviewFacade;
    @Inject
    private EntityValidator validator;

    @Override
    protected AbstractHome<Review> getHome() {
        return reviewHome;
    }

    @Override
    protected AbstractFacadeInt<Review> getFacade() {
        return reviewFacade;
    }

    public ValidationResult validate(Review review) {
        return validator.validate(review);
    }

    public void update(Review review) {
        reviewHome.update(review);
    }

    public void delete(Review review){
        reviewHome.delete(review);
    }
}
