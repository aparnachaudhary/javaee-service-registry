package io.github.aparnachaudhary.javaee.service.registry;

import java.io.Serializable;

/**
 * @author Aparna Chaudhary
 */
public class EndpointId implements Serializable {

    private String nodeName;
    private String appName;

    private EndpointId() {
    }

    public EndpointId(String nodeName, String appName) {
        this.nodeName = nodeName;
        this.appName = appName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public String toString() {
        return "EndpointId{" +
                "nodeName='" + nodeName + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }

    public static final class EndpointIdBuilder {

        private String nodeName;
        private String appName;

        private EndpointIdBuilder() {
        }

        public static EndpointIdBuilder newBuilder() {
            return new EndpointIdBuilder();
        }

        public EndpointIdBuilder setNodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public EndpointIdBuilder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public EndpointId createEndpointId() {
            return new EndpointId(nodeName, appName);
        }
    }
}