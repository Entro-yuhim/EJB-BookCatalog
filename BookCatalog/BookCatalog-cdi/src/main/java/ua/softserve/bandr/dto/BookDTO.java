package ua.softserve.bandr.dto;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;

import java.util.Date;
import java.util.List;

/**
 * Created by bandr on 29.01.2016.
 */
public class BookDTO implements EntityDTO {
	private static final long serialVersionUID = -4613984916560535321L;
	private Long id;
	private String title;
	private List<AuthorDTO> authorNames;
	private String iSBN;
	private Date dateCreated;
	private Integer rating;

	public BookDTO(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.rating = book.getRating();
		this.iSBN = book.getiSBN();
		this.dateCreated = book.getCreateDate();
		authorNames = Lists.newArrayList();
		for (Author a : book.getAuthors()) {
			authorNames.add(new AuthorDTO(a));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<AuthorDTO> getAuthorNames() {
		return authorNames;
	}

	public void setAuthorNames(List<AuthorDTO> authorNames) {
		this.authorNames = authorNames;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getiSBN() {
		return iSBN;
	}

	public void setiSBN(String iSBN) {
		this.iSBN = iSBN;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public Object getKey() {
		return this.id;
	}

	private static class AuthorNameConverter implements Function<Author, String> {
		@Override
		public String apply(Author input) {
			return input.getFirstName() + " " + (input.getLastName() == null ? "" : input.getLastName());
		}
	}
}
