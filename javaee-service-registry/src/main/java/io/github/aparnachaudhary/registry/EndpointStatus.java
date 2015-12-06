package io.github.aparnachaudhary.registry;

/**
 * Status of service.
 *
 * @author Aparna Chaudhary
 */
public enum EndpointStatus {

    STARTING,
    UP,
    STOPPING,
    DOWN,
    UNKNOWN;

    public static EndpointStatus toEnum(String s) {
        for (EndpointStatus e : EndpointStatus.values()) {
            if (e.name().equalsIgnoreCase(s)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
