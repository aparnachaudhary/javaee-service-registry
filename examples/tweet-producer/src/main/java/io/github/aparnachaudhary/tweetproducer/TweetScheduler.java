package io.github.aparnachaudhary.tweetproducer;

import io.github.aparnachaudhary.javaee.service.interceptor.LifecycleTimerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import java.util.Date;

/**
 * @author Aparna
 */
@Stateless
public class TweetScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(TweetScheduler.class);


    @LifecycleTimerInterceptor
    @Timeout
    @Schedule(hour = "*", minute = "*", second = "30/10")
    public void processTweets() {
        LOG.info("Starting to process tweets {}", new Date());
    }
}
