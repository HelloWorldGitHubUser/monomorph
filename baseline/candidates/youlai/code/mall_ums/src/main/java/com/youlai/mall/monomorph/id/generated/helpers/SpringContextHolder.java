package com.youlai.mall.monomorph.id.generated.helpers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Holds a static reference to the Spring {@link ApplicationContext} after the
 * monolith application context has been created.
 *
 * <p>This class lives in the generated monomorph package so that it is picked up
 * by the existing component scan ({@code com.youlai.mall}) without modifying the
 * original application sources. Generated gRPC service implementations that need
 * a Spring-managed business bean can obtain it via
 * {@link #getBean(Class)} instead of constructing it manually.</p>
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static volatile ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * Returns the Spring {@link ApplicationContext}, or {@code null} if it has not
     * been initialized yet.
     *
     * @return the current application context, may be {@code null}
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Returns the bean matching the given type from the Spring application context.
     *
     * @param requiredType the type the bean must match
     * @param <T>          the bean type
     * @return the bean instance
     * @throws IllegalStateException if the application context is not available yet
     */
    public static <T> T getBean(Class<T> requiredType) {
        ApplicationContext ctx = applicationContext;
        if (ctx == null) {
            throw new IllegalStateException(
                    "Spring ApplicationContext has not been initialized yet. "
                            + "Make sure the monolith application is started before using generated services.");
        }
        return ctx.getBean(requiredType);
    }
}

