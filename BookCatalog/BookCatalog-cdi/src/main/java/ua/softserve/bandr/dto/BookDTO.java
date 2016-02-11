package ua.softserve.bandr.dto;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;

/**
 * Created by bandr on 29.01.2016.
 */
public class BookDTO implements EntityDTO {
	private static final long serialVersionUID = -4613984916560535321L;
	private Long id;
	private String title;
	private String authorNames;
	private String iSBN;
	private Integer rating;

	public BookDTO(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.authorNames = StringUtils.join(Iterables.transform(book.getAuthors(), new AuthorNameConverter()), ", ");
		this.rating = book.getRating();
		this.iSBN = book.getiSBN();
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

	public String getAuthorNames() {
		return authorNames;
	}

	public void setAuthorNames(String authorNames) {
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
