package ua.softserve.bandr.ws;

import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.persistence.manager.BookManager;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by bandr on 26.01.2016.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/book")
public class BookService {
    @Inject
    private BookManager bookManager;

    @GET
    @Path("/{id:[0-9]+}")
    public Response getBook(@PathParam("id") Long id){
        return Response.ok(bookManager.getById(id)).build();
    }

    @POST
    @Path("/")
    public Response saveBook(@Valid Book book){
        bookManager.persist(book);
        return Response.ok().build();
    }

    @POST
    @Path("/{id:[0-9]+}")
    public Response updateBook(@Valid Book book, @PathParam("id") Long id){
        Book present = bookManager.getById(id);
        if (present == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        book.setId(present.getId());
        bookManager.update(book);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    public Response deleteBook(@PathParam("id") Long id){
        Book book = bookManager.getById(id);
        if (book == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        bookManager.delete(book);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
