package ua.softserve.bandr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.softserve.bandr.entity.Review;

/**
 * Created by bandr on 11.02.2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDTO {
	private Long id;
	private String username;
	private String reviewText;
	private Integer rating;
	private Long bookId;

	public ReviewDTO() {
	}

	private ReviewDTO(Review review) {
		this.id = review.getId();
		this.username = review.getUsername();
		this.reviewText = review.getReviewText();
		this.bookId = review.getBook().getId();
		this.rating = review.getRating();
	}

	public static ReviewDTO fromEntity(Review review) {
		return new ReviewDTO(review);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@JsonIgnore
	public Review getReviewData() {
		Review r = new Review();
		r.setId(this.id);
		r.setReviewText(this.reviewText);
		r.setUsername(this.username);
		r.setRating(this.rating);
		return r;
	}
}
