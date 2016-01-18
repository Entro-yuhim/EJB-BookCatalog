package ua.softserve.bandr.ejb.facades;

import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ReviewFacade {
    private QueryManager<Review> reviewQueryManager;

    public List<Review> getAll(){
        return null;
        //return reviewQueryManager.executeQuery("Reviews.getAll", Review.class);
    }
}
