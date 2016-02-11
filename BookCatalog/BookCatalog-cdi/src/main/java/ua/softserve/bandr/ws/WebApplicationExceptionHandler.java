package ua.softserve.bandr.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by bandr on 26.01.2016.
 */
@Provider
public class WebApplicationExceptionHandler implements ExceptionMapper<WebApplicationException> {
	private static final Logger LOG = LoggerFactory.getLogger(WebApplicationExceptionHandler.class);

	@Override
	public Response toResponse(WebApplicationException exception) {
		LOG.info("Received incorrect request [{}]", exception.getMessage());
		return exception.getResponse();
	}
}
