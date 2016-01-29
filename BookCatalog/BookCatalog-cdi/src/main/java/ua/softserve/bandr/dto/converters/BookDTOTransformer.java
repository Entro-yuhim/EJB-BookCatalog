package ua.softserve.bandr.dto.converters;

import com.google.common.base.Function;
import ua.softserve.bandr.dto.BookDTO;
import ua.softserve.bandr.entity.Book;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Created by bandr on 29.01.2016.
 */
public class BookDTOTransformer extends DTOTransformer<Book, BookDTO> {
	@Override
	protected Function<Book, BookDTO> getConverterFunction() {
		return new BookToDTOConverter();
	}

	private static class BookToDTOConverter implements Function<Book, BookDTO> {
		@Override
		@TransactionAttribute(TransactionAttributeType.MANDATORY)
		public BookDTO apply(Book input) {
			return new BookDTO(input);
		}
	}
}
