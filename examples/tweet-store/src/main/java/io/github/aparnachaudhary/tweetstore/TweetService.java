package io.github.aparnachaudhary.tweetstore;

import io.github.aparnachaudhary.tweet.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ws.rs.PathParam;

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
