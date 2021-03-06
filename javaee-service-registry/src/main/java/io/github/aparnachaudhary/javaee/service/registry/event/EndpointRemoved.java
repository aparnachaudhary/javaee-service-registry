package io.github.aparnachaudhary.javaee.service.registry.event;

import io.github.aparnachaudhary.javaee.service.registry.EndpointId;

/**
 * @author Aparna Chaudhary
 */
public class EndpointRemoved implements EndpointEvent {

    private EndpointId endpointId;

    public EndpointRemoved(EndpointId endpointId) {
        this.endpointId = endpointId;
    }

    public EndpointId getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(EndpointId endpointId) {
        this.endpointId = endpointId;
    }
}
