package ua.softserve.bandr.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by bandr on 26.01.2016.
 */
@Provider
public class NotAllowedHandler implements ExceptionMapper<NotAllowedException> {
    private static final Logger LOG = LoggerFactory.getLogger(NotAllowedHandler.class);
    @Override
    public Response toResponse(NotAllowedException exception) {
        LOG.info("Received incorrect request [{}]", exception.getMessage());
        return exception.getResponse();
    }
}
