package ua.softserve.bandr.ws.client;

import ua.softserve.bandr.dto.ReviewDTO;
import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by bandr on 11.02.2016.
 */
@Stateless
public class ReviewClient {
	private static final String SERVER_TARGET_PATH = "http://localhost:8080/bandr-ejb/webservice";
	public Review getReviewById(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SERVER_TARGET_PATH + "/review/").path("/{reviewId}");
		return target.resolveTemplate("reviewId", id).request(MediaType.APPLICATION_JSON).get(Review.class);
	}

	public void persistReview(ReviewDTO review) throws WebServiceClientException {
		// FIXME: 11.02.2016 should throw exception if something goes wrong
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SERVER_TARGET_PATH + "/review");
		Response post = target.request().post(Entity.entity(review, MediaType.APPLICATION_JSON));
		if (post.getStatus() != 200) {
			throw new WebServiceClientException();
		}
	}
}
