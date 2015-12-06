package io.github.aparnachaudhary.registry.infinispan;

import io.github.aparnachaudhary.registry.EndpointId;
import io.github.aparnachaudhary.registry.EndpointInfo;
import io.github.aparnachaudhary.registry.EndpointRegistry;
import io.github.aparnachaudhary.registry.event.EndpointEvent;
import io.github.aparnachaudhary.registry.util.PojoMapper;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

/**
 * Regarding use of {@link ManagedBean} - See https://developer.jboss.org/message/872450
 *
 * @author Aparna Chaudhary
 */
//@ApplicationScoped
@ManagedBean
public class InfinispanRegistry implements EndpointRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(InfinispanRegistry.class);

    private static final String NODE_NAME = "jboss.node.name";

    @Resource(lookup = "java:jboss/infinispan/container/serviceRegistry")
    private CacheContainer cc;

    @Resource(lookup = "java:app/AppName")
    private String appName;

    private Map<String, String> cache;

    @Inject
    private Event<EndpointEvent> event;

    @PostConstruct
    public void init() {
        this.cache = cc.getCache();
        cc.getCache().getCacheManager().addListener(new MemberDropListener(cache));
        cc.getCache().addListener(new ClusteredCacheListener(event));
        LOG.info("Cache {}", cc.getCache().keySet());
    }

    @Override
    public void addEndpoint(EndpointInfo endpointInfo) {
        LOG.info("Adding endpoint '{}' to registry ", endpointInfo.getEndpointId());
        try {
            cache.put(PojoMapper.toJson(endpointInfo.getEndpointId()), PojoMapper.toJson(endpointInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEndpoint(EndpointId endpointId) {
        LOG.info("Removing endpoint '{}' from registry", endpointId);
        try {
            cache.remove(PojoMapper.toJson(endpointId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<EndpointInfo> getEndpoints() {
        List<EndpointInfo> endpointInfoList = new ArrayList<EndpointInfo>();
        for (String value : cache.values()) {
            try {
                endpointInfoList.add(PojoMapper.fromJson(value, EndpointInfo.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return endpointInfoList;
    }

    @Override
    public boolean existsEndpoint(String appName) {
        boolean endpointExists = false;
        EndpointId endpointId;
        for (String key : cache.keySet()) {
            try {
                endpointId = PojoMapper.fromJson(key, EndpointId.class);
                if (endpointId.getAppName().equalsIgnoreCase(appName)) {
                    endpointExists = true;
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return endpointExists;
    }

    @Override
    public EndpointInfo getEndpoint(EndpointId endpointId) {
        try {
            String value = cache.get(PojoMapper.toJson(endpointId));
            if (value != null) {
                return PojoMapper.fromJson(value, EndpointInfo.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsDependencies(EndpointInfo endpointInfo) {
        Set<EndpointId> dependencies = endpointInfo.getDependencies();
        for (EndpointId dependency : dependencies) {
            if (!existsEndpoint(dependency.getAppName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public EndpointId getCurrentEndpoint() {
        EndpointId endpointId = EndpointId.EndpointIdBuilder.newBuilder()
                .setNodeName(System.getProperty(NODE_NAME))
                .setAppName(appName)
                .createEndpointId();
        return endpointId;
    }

}
