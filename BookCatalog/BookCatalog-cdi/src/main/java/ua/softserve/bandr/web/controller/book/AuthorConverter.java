package ua.softserve.bandr.web.controller.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.entity.Author;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 * Created by bandr on 12.02.2016.
 */
@FacesConverter("authorConverter")
public class AuthorConverter implements Converter {
	private static final Logger LOG = LoggerFactory.getLogger(AuthorConverter.class);

	//Returns string instead of object. For whatever reason.
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		LOG.info("getAsObject [{}]", value);
		return Long.valueOf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Author author = (Author) value;
		return author.getId().toString();
	}
}
