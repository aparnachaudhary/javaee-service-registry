package io.github.aparnachaudhary.tweetproducer;

import io.github.aparnachaudhary.registry.EndpointInfo;
import io.github.aparnachaudhary.registry.EndpointRegistry;
import io.github.aparnachaudhary.registry.util.PojoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Default service implementation for {@link TweetResource}
 *
 * @author Aparna Chaudhary
 */
@Stateless
public class TweetService implements TweetResource {

    private static final Logger LOG = LoggerFactory.getLogger(TweetService.class);

    @Inject
    private EndpointRegistry endpointRegistry;

    @Override
    public String sayHi() {
        for (EndpointInfo endpoint : endpointRegistry.getEndpoints()) {
            LOG.info("Endpoint Name {}", endpoint);
        }
        try {
            return PojoMapper.toJson(endpointRegistry.getEndpoint(endpointRegistry.getCurrentEndpoint()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Blah";
    }

}
