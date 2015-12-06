package io.github.aparnachaudhary.tweetstore;

import io.github.aparnachaudhary.registry.EndpointInfo;
import io.github.aparnachaudhary.registry.EndpointRegistry;
import io.github.aparnachaudhary.registry.util.PojoMapper;
import io.github.aparnachaudhary.tweet.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import java.io.IOException;

/**
 * Default service implementation for {@link TweetResource}
 *
 * @author Aparna Chaudhary
 */
@Stateless
public class TweetService implements TweetResource {

    private static final Logger LOG = LoggerFactory.getLogger(TweetService.class);

    @Override
    public Tweet get(@PathParam("id") String id) {
        return new Tweet(id, "aparnachaudhary", "SomeMessage");
    }

    @Override
    public String store(Tweet tweet) {
        return null;
    }
}
