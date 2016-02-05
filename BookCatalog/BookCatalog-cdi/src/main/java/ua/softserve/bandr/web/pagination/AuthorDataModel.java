package ua.softserve.bandr.web.pagination;

import com.google.common.base.Function;
import org.richfaces.component.SortOrder;
import ua.softserve.bandr.dto.AuthorDTO;
import ua.softserve.bandr.dto.converters.DTOTransformer;
import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.persistence.manager.AuthorManager;
import ua.softserve.bandr.web.pagination.richmodels.AbstractDTODataModel;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by bandr on 02.02.2016.
 */
@Stateful
public class AuthorDataModel extends AbstractDTODataModel<Author, AuthorDTO> {

	@Inject
	private AuthorManager authorManager;

	@Override
	protected List<Author> getPersistablesList(Integer firstRow, Integer numRows, Map<String, String> filter, Map<String, SortOrder> sortingMap) {
		return authorManager.getPagedFiltered(firstRow, numRows, filter, convertSortingToOrder(sortingMap));
	}

	@Override
	protected DTOTransformer<Author, AuthorDTO> getDTOTransformer() {
		return new AuthorAuthorDTODTOTransformer();
	}

	@Override
	public Long getTotalCount(Map<String, String> filter) {
		return authorManager.getRecordCount(filter);
	}

	private static class AuthorAuthorDTOFunction implements Function<Author, AuthorDTO> {
		@Override
		public AuthorDTO apply(Author input) {
			return new AuthorDTO(input);
		}
	}

	private static class AuthorAuthorDTODTOTransformer extends DTOTransformer<Author, AuthorDTO> {
		@Override
		protected Function<Author, AuthorDTO> getConverterFunction() {
			return new AuthorAuthorDTOFunction();
		}
	}
}
