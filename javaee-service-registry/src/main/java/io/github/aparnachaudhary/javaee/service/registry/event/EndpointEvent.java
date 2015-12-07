package io.github.aparnachaudhary.javaee.service.registry.event;

import io.github.aparnachaudhary.javaee.service.registry.EndpointId;

/**
 * @author Aparna Chaudhary
 */
public interface EndpointEvent {

    EndpointId getEndpointId();

    void setEndpointId(EndpointId endpointId);
}
