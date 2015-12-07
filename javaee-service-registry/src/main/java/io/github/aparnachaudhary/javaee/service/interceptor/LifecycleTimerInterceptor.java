package io.github.aparnachaudhary.javaee.service.interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Aparna
 */
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface LifecycleTimerInterceptor {

}
