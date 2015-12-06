package io.github.aparnachaudhary.registrant;

import io.github.aparnachaudhary.registry.EndpointId;
import io.github.aparnachaudhary.registry.EndpointInfo;
import io.github.aparnachaudhary.registry.EndpointRegistry;
import io.github.aparnachaudhary.registry.EndpointStatus;
import io.github.aparnachaudhary.registry.event.EndpointAdded;
import io.github.aparnachaudhary.registry.event.EndpointRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author Aparna
 */
@ApplicationScoped
public class ApplicationRegistrant {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRegistrant.class);

    private static final String NODE_NAME = "jboss.node.name";
    private static final String PRODUCER_APP_NAME = "feed-producer";

    @Inject
    private EndpointRegistry endpointRegistry;

    @Resource(lookup = "java:app/AppName")
    private String appName;

    /**
     * @param init
     */
    public void onInitialize(@Observes @Initialized(ApplicationScoped.class) Object init) {
        LOG.info("Started : {}", init);
        register();
    }

    /**
     * @param init
     */
    public void onDestroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        LOG.info("Destroyed : {}", init);
        unregister();
    }

    /**
     * @param endpointAdded
     */
    public void onEndpointAdded(@Observes EndpointAdded endpointAdded) {
        EndpointId endpointId = endpointAdded.getEndpointId();
        LOG.info("=========== endpointAdded: {} ===========", endpointId);
        register();
    }

    /**
     * @param endpointRemoved
     */
    public void onEndpointRemoved(@Observes EndpointRemoved endpointRemoved) {
        EndpointId endpointId = endpointRemoved.getEndpointId();
        LOG.info("=========== endpointRemoved: {} ===========", endpointId);
        if (!endpointRegistry.existsDependencies(getLocalEndpoint())) {
            unregister();
        }
    }

    private void register() {
        EndpointInfo endpointInfo = getLocalEndpoint();
        if (endpointRegistry.existsDependencies(endpointInfo)) {
            endpointRegistry.addEndpoint(endpointInfo);
        } else {
            LOG.warn("All dependent Services are not yet available for {}", endpointInfo.getEndpointId());
        }
    }

    private void unregister() {
        EndpointInfo consumerEndpointInfo = endpointRegistry.getEndpoint(getLocalEndpoint().getEndpointId());
        if (consumerEndpointInfo != null) {
            endpointRegistry.removeEndpoint(getLocalEndpoint().getEndpointId());
        }
    }

    private EndpointInfo getLocalEndpoint() {
        EndpointId endpointId = EndpointId.EndpointIdBuilder.newBuilder()
                .setNodeName(System.getProperty(NODE_NAME)).setAppName(appName)
                .createEndpointId();
//        EndpointId producerEndpointId = EndpointId.EndpointIdBuilder.newBuilder()
//                .setAppName(PRODUCER_APP_NAME)
//                .createEndpointId();
        return EndpointInfo.EndpointInfoBuilder.newBuilder()
                .setEndpointId(endpointId)
                .setStatus(EndpointStatus.STARTING)
                .createEndpointInfo();
    }

}
