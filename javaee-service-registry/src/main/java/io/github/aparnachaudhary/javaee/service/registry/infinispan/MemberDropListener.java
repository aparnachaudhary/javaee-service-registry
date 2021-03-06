package io.github.aparnachaudhary.javaee.service.registry.infinispan;

import io.github.aparnachaudhary.javaee.service.registry.EndpointId;
import io.github.aparnachaudhary.javaee.service.registry.util.PojoMapper;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachemanagerlistener.annotation.ViewChanged;
import org.infinispan.notifications.cachemanagerlistener.event.ViewChangedEvent;
import org.infinispan.remoting.transport.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cache listener for ViewChangedEvent.
 *
 * @author Aparna Chaudhary
 */
@Listener(clustered = true, sync = false, observation = Listener.Observation.POST)
public class MemberDropListener {

    private static final Logger LOG = LoggerFactory.getLogger(MemberDropListener.class);

    private final Map<String, String> cache;

    public MemberDropListener(Map<String, String> cache) {
        this.cache = cache;
    }

    /**
     * Triggered when a view is changed, signaling that a member has joined or dropped from the cluster.
     *
     * @param event change details
     */
    @ViewChanged
    public void viewChanged(ViewChangedEvent event) {
        List<Address> newMembers = event.getNewMembers();
        List<Address> oldMembers = event.getOldMembers();
        LOG.info("Membership changed. New cluster size is " + newMembers.size());

        List<Address> dropped = minus(oldMembers, newMembers);
        List<Address> joined = minus(newMembers, oldMembers);
        for (Address address : dropped) {
            LOG.info("Node '{}' left the cluster", address);
            dropAllServices(address);
        }

        for (Address address : joined) {
            LOG.info("Node '{}' joined the cluster", address);
        }
    }

    private List<Address> minus(List<Address> source, List<Address> remove) {
        List<Address> result = new ArrayList(source);
        result.removeAll(remove);
        return result;
    }

    void dropAllServices(Address address) {
        if (cache == null) {
            LOG.warn("Cache is null");
        } else {
            cache.keySet().stream().filter(
                    cacheKey -> {
                        try {
                            return PojoMapper.fromJson(cacheKey, EndpointId.class).getNodeName().equalsIgnoreCase(address.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }).forEach(cache::remove);
        }
    }
}


