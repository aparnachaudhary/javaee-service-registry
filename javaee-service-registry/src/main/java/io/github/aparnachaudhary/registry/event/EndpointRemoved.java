package io.github.aparnachaudhary.registry.event;

import io.github.aparnachaudhary.registry.EndpointId;

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
