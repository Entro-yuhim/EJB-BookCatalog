package ua.softserve.bandr.dto;

import ua.softserve.bandr.entity.Author;

/**
 * Created by bandr on 02.02.2016.
 */
public class AuthorDTO implements EntityDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private Integer rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public AuthorDTO(Author author) {
		this.id = author.getId();
		this.firstName = author.getFirstName();
		this.lastName = author.getLastName();
		this.rating = author.getRating();
	}

	private static final long serialVersionUID = 5135030710117458349L;

	@Override
	public Object getKey() {
		return this.id;
	}
}
