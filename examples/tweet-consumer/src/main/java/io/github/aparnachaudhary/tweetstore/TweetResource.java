package io.github.aparnachaudhary.tweetstore;

import io.github.aparnachaudhary.tweet.Tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * REST Endpoint for feed producer
 *
 * @author Aparna Chaudhary
 */
@Path("/tweets")
public interface TweetResource {

    @Path("{id}")
    @GET
    @Produces({"application/json"})
    Tweet get(@PathParam("id") String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    String store(Tweet tweet);


}
