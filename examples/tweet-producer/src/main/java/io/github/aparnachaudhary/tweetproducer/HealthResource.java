package io.github.aparnachaudhary.tweetproducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Endpoint for health check
 *
 * @author Aparna Chaudhary
 */
@Path("/health")
public interface HealthResource {

    @GET
    @Produces({"application/json"})
    String getHealth();

}
