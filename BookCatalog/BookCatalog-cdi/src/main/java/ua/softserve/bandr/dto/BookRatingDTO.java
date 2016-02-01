package ua.softserve.bandr.dto;

import java.io.Serializable;

/**
 * Created by bandr on 19.01.2016.
 */
public class BookRatingDTO implements Serializable {
	private static final long serialVersionUID = -2742636080563238689L;
	private Integer rating;
	private Long count;

	public BookRatingDTO(Integer rating, Long count) {
		this.rating = rating;
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
