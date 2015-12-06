package io.github.aparnachaudhary.registry;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aparna Chaudhary
 */
public class EndpointInfo implements Serializable {

    private EndpointId endpointId;
    private EndpointStatus status;
    private Set<EndpointId> dependencies = Collections.synchronizedSet(new HashSet<>());

    public EndpointInfo() {
    }

    public EndpointInfo(EndpointId endpointId, EndpointStatus status, Set<EndpointId> dependencies) {
        this.endpointId = endpointId;
        this.status = status;
        this.dependencies = dependencies;
    }

    public EndpointId getEndpointId() {
        return endpointId;
    }

    public EndpointStatus getStatus() {
        return status;
    }

    public Set<EndpointId> getDependencies() {
        return dependencies;
    }

    @Override
    public String toString() {
        return "EndpointInfo{" +
                "endpointId=" + endpointId +
                ", status=" + status +
                ", dependencies=" + dependencies +
                '}';
    }

    public static class EndpointInfoBuilder {
        private EndpointId endpointId;
        private EndpointStatus status;
        private Set<EndpointId> dependencies = Collections.synchronizedSet(new HashSet<>());

        public EndpointInfoBuilder() {
        }

        public static EndpointInfoBuilder newBuilder() {
            return new EndpointInfoBuilder();
        }

        public EndpointInfoBuilder setEndpointId(EndpointId endpointId) {
            this.endpointId = endpointId;
            return this;
        }

        public EndpointInfoBuilder setStatus(EndpointStatus status) {
            this.status = status;
            return this;
        }

        public EndpointInfoBuilder addDependency(EndpointId dependency) {
            this.dependencies.add(dependency);
            return this;
        }

        public EndpointInfoBuilder setDependencies(Set<EndpointId> dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public EndpointInfo createEndpointInfo() {
            return new EndpointInfo(endpointId, status, dependencies);
        }
    }
}
