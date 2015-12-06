package io.github.aparnachaudhary.tweetproducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Endpoint for feed producer
 *
 * @author Aparna Chaudhary
 */
@Path("/tweet")
public interface TweetResource {

    @Path("greet")
    @GET
    @Produces({ "application/json" }) String sayHi();

}
