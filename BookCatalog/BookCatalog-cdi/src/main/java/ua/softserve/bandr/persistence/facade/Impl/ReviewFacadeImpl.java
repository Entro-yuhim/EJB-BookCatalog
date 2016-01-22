package ua.softserve.bandr.persistence.facade.Impl;

import com.google.common.base.Optional;
import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.facade.ReviewFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

@LocalBean
@Stateless
public class ReviewFacadeImpl extends AbstractFacade<Review> implements ReviewFacade {

    @Override
    public List<Review> getAll() {
        return executeNamedQuery(Review.GET_ALL);
    }

    @Override
    public List<Review> getPaged(Integer startWith, Integer pageSize){
        return executeNamedQuery(Review.GET_ALL, Optional.of(startWith), Optional.of(pageSize));
    }
}
