package io.github.aparnachaudhary.javaee.service.interceptor;

import io.github.aparnachaudhary.javaee.service.registry.EndpointInfo;
import io.github.aparnachaudhary.javaee.service.registry.EndpointStatus;
import io.github.aparnachaudhary.javaee.service.registry.EndpointRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Interceptor to invoke timer bean only if the component status is UP.
 *
 * @author Aparna
 */
@Interceptor
@LifecycleTimerInterceptor
public class TimerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(TimerInterceptor.class);

    @Inject
    EndpointRegistry endpointRegistry;

    @AroundTimeout
    public Object aroundTimeout(final InvocationContext context) throws Exception {
        Object proceed = null;
        EndpointInfo endpointInfo = endpointRegistry.getEndpoint(endpointRegistry.getCurrentEndpoint());
        if (endpointInfo != null && endpointInfo.getStatus().equals(EndpointStatus.UP)) {
            LOG.info("Current Status {}", endpointInfo);
            proceed = context.proceed();
        }
        return proceed;
    }
}
