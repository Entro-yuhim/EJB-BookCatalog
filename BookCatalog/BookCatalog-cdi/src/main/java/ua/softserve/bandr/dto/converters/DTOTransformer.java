package ua.softserve.bandr.dto.converters;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.dto.EntityDTO;
import ua.softserve.bandr.entity.Persistable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

/**
 * Created by bandr on 29.01.2016.
 */
public abstract class DTOTransformer<U extends Persistable, T extends EntityDTO> {
	private static final Logger LOG = LoggerFactory.getLogger(DTOTransformer.class);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<T> getDTOList(List<U> inputList) {
		LOG.info("Transforming data list with given converter");
		return Lists.transform(inputList, getConverterFunction());
	}

	protected abstract Function<U, T> getConverterFunction();
}
