package ua.softserve.bandr.ws;

import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.manager.ReviewManager;

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
@Path("/review")
public class ReviewService {

	@Inject
	private ReviewManager reviewManager;

	@GET
	@Path("/{id:[0-9]+}")
	public Response getReview(@Valid @PathParam("id") Long id) {
		return Response.ok(reviewManager.getById(id)).build();
	}


	@POST
	@Path("/")
	public Response saveReview(@Valid Review review) {
		reviewManager.persist(review);
		return Response.ok().build();
	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateReview(@Valid Review review, @PathParam("id") Long id) {
		Review present = reviewManager.getById(id);
		if (present == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		review.setId(present.getId());
		review.setBook(present.getBook());
		reviewManager.update(review);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id:[0-9]}")
	public Response removeReview(@PathParam("id") Long id) {
		Review review = reviewManager.getById(id);
		if (review == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		reviewManager.delete(review);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
