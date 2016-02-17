package ua.softserve.bandr.ws;

import ua.softserve.bandr.entity.Review;
import ua.softserve.bandr.persistence.exceptions.ConstraintCheckException;
import ua.softserve.bandr.persistence.exceptions.PersistenceException;
import ua.softserve.bandr.persistence.manager.ReviewManager;
import ua.softserve.bandr.ws.dto.ReviewDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

	@GET
	@Path("/byBook/{id:[0-9]+}")
	public Response getByBookId(@Valid @PathParam("id") Long id) {
		return Response.ok(reviewManager.getByBookId(id)).build();
	}


	@POST
	@Path("/")
	public Response saveReview(@Valid ReviewDTO review) {
		Review newReview = review.getReviewData();
		try {
			reviewManager.persistWithBookId(newReview, review.getBookId());
		} catch (PersistenceException e) {
			Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().build();
	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateReview(@Valid ReviewDTO reviewDTO, @PathParam("id") Long id) {
		if (reviewDTO.getId() != null || reviewDTO.getBookId() != null) {
			return Response.status(Response.Status.CONFLICT).build();
		}

		Review review = reviewDTO.getReviewData();
		review.setId(id);
		try {
			reviewManager.update(review);
		} catch (PersistenceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(ReviewDTO.fromEntity(review)).build();
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
		try {
			reviewManager.update(review);
		} catch (PersistenceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id:[0-9]}")
	public Response removeReview(@PathParam("id") Long id) {
		Review review = reviewManager.getById(id);
		if (review == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		try {
			reviewManager.delete(review);
		} catch (PersistenceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
