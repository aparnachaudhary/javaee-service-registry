package io.github.aparnachaudhary.registrant;

import io.github.aparnachaudhary.registry.*;
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
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aparna
 */
@ApplicationScoped
public class ApplicationRegistrant {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRegistrant.class);

    private static final String NODE_NAME = "jboss.node.name";
    private static final String SERVICE_DEP_JNDI = "java:app/serviceDependency";

    @Inject
    private EndpointRegistry endpointRegistry;

    @Resource(lookup = "java:app/AppName")
    private String appName;

    private EndpointInfo endpointInfo;

    /**
     * @param init
     */
    public void onInitialize(@Observes @Initialized(ApplicationScoped.class) Object init) {
        LOG.info("Started : {}", init);
        String dependencies = null;
        try {
            InitialContext initialContext = new InitialContext();
            dependencies = (String) initialContext.lookup(SERVICE_DEP_JNDI);
        } catch (NameNotFoundException e) {
            LOG.info("No dependencies defined");
        } catch (NamingException e) {
            throw new IllegalStateException("Failed to initialize");
        }
        // current endpoint
        endpointInfo = getLocalEndpoint(dependencies);
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
        LOG.info("-------------------------------------------------------------------");
        LOG.info("endpointAdded: {}", endpointId);
        LOG.info("-------------------------------------------------------------------");
        register();
    }

    /**
     * @param endpointRemoved
     */
    public void onEndpointRemoved(@Observes EndpointRemoved endpointRemoved) {
        EndpointId endpointId = endpointRemoved.getEndpointId();
        LOG.info("-------------------------------------------------------------------");
        LOG.info("endpointRemoved: {}", endpointId);
        LOG.info("-------------------------------------------------------------------");
        if (!endpointRegistry.existsDependencies(endpointInfo)) {
            unregister();
        }
    }

    private void register() {
        if (endpointRegistry.existsDependencies(endpointInfo)) {
            endpointInfo.setStatus(EndpointStatus.UP);
            LOG.info("*******************************************************************");
            endpointRegistry.addEndpoint(endpointInfo);
            LOG.info("*******************************************************************");
        } else {
            LOG.warn("All required services are not yet available for {}", endpointInfo.getEndpointId());
        }
    }

    private void unregister() {
        EndpointInfo localEndpointInfo = endpointRegistry.getEndpoint(endpointInfo.getEndpointId());
        if (localEndpointInfo != null) {
            LOG.info("*******************************************************************");
            endpointRegistry.removeEndpoint(localEndpointInfo.getEndpointId());
            LOG.info("*******************************************************************");
        }
    }

    private EndpointInfo getLocalEndpoint(String dependencies) {
        EndpointId endpointId = EndpointId.EndpointIdBuilder.newBuilder()
                .setNodeName(System.getProperty(NODE_NAME)).setAppName(appName)
                .createEndpointId();

        Set<DependencyId> dependencyIds = new HashSet<>();

        if (dependencies != null) {
            LOG.info("$$$$$$$$$ {}", dependencies);
            DependencyId dependencyId = DependencyId.DependencyIdBuilder.newBuilder()
                    .setAppName(dependencies)
                    .createEndpointId();
            dependencyIds.add(dependencyId);
        }

        return EndpointInfo.EndpointInfoBuilder.newBuilder()
                .setEndpointId(endpointId)
                .setStatus(EndpointStatus.STARTING)
                .setDependencies(dependencyIds)
                .createEndpointInfo();
    }

}
