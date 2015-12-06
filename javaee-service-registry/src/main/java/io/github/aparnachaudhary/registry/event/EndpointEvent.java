package io.github.aparnachaudhary.registry.event;

import io.github.aparnachaudhary.registry.EndpointId;

/**
 * @author Aparna Chaudhary
 */
public interface EndpointEvent {

    EndpointId getEndpointId();

    void setEndpointId(EndpointId endpointId);
}
