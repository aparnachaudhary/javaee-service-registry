package io.github.aparnachaudhary.tweetproducer;

import io.github.aparnachaudhary.javaee.service.registry.EndpointInfo;
import io.github.aparnachaudhary.javaee.service.registry.EndpointRegistry;
import io.github.aparnachaudhary.javaee.service.registry.util.PojoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Default service implementation for {@link HealthResource}
 *
 * @author Aparna Chaudhary
 */
@Stateless
public class HealthService implements HealthResource {

    private static final Logger LOG = LoggerFactory.getLogger(HealthService.class);

    @Inject
    private EndpointRegistry endpointRegistry;

    @Override
    public String getHealth() {
        for (EndpointInfo endpoint : endpointRegistry.getEndpoints()) {
            LOG.info("Endpoint Name {}", endpoint);
        }
        String result = null;
        try {
            EndpointInfo endpoint = endpointRegistry.getEndpoint(endpointRegistry.getCurrentEndpoint());
            if (endpoint != null) {
                result = PojoMapper.toJson(endpoint);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null) {
            throw new WebApplicationException("No Endpoint registered", Response.serverError().entity("No Endpoint registered").build());
        }
        return result;
    }

}
