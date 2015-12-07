package io.github.aparnachaudhary.javaee.service.registry;

import java.io.Serializable;

/**
 * @author Aparna Chaudhary
 */
public class DependencyId implements Serializable {

    private String appName;

    private DependencyId() {
    }

    public DependencyId(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public String toString() {
        return "EndpointId{" +
                "appName='" + appName + '\'' +
                '}';
    }

    public static final class DependencyIdBuilder {

        private String appName;

        private DependencyIdBuilder() {
        }

        public static DependencyIdBuilder newBuilder() {
            return new DependencyIdBuilder();
        }

        public DependencyIdBuilder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public DependencyId createEndpointId() {
            return new DependencyId(appName);
        }
    }
}