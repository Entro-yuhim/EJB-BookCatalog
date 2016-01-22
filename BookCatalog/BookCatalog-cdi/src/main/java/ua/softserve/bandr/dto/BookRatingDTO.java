package ua.softserve.bandr.dto;

/**
 * Created by bandr on 19.01.2016.
 */
public class BookRatingDTO {
    private Integer rating;
    private Long count;

    public BookRatingDTO(Integer rating, Long count) {
        this.rating = rating.intValue();
        this.count = count;
    }

    public BookRatingDTO() {
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
