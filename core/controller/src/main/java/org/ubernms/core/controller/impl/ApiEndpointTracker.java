package org.ubernms.core.controller.impl;

import com.google.inject.Injector;
import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.spi.ResourceFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ubernms.core.controller.spi.ComponentDescriptor;

public class ApiEndpointTracker extends ServiceTracker<ComponentDescriptor, ComponentDescriptor> {
    private final static Logger LOG = LoggerFactory.getLogger(ApiEndpointTracker.class);

    private final BundleContext bundleContext;

    private final Injector injector;

    private final HttpServletDispatcher dispatcher;


    public ApiEndpointTracker(final BundleContext bundleContext,
                              final Injector injector,
                              final HttpServletDispatcher dispatcher) {
        super(bundleContext,
              ComponentDescriptor.class,
              null);

        this.bundleContext = bundleContext;
        this.injector = injector;
        this.dispatcher = dispatcher;
    }

    @Override
    public ComponentDescriptor addingService(final ServiceReference<ComponentDescriptor> reference) {
        final ComponentDescriptor componentDescriptor = this.bundleContext.getService(reference);

        LOG.info("Adding component: {}", componentDescriptor.getId());

        // TODO: Use classpath scanning on component classpath instead
        for (final Class<?> clazz : componentDescriptor.getApiEndpoints()) {
//            final ResourceFactory resourceFactory = new POJOResourceFactory(clazz);
            final ResourceFactory resourceFactory = new GuiceResourceFactory(injector.getProvider(clazz), clazz);

            this.dispatcher.getDispatcher()
                           .getRegistry()
                           .addResourceFactory(resourceFactory,
                                               calculateBasePath(reference));
        }

        return componentDescriptor;
    }

    @Override
    public void removedService(final ServiceReference<ComponentDescriptor> reference, final ComponentDescriptor componentDescriptor) {
        LOG.info("Removed component: {}", componentDescriptor.getId());

        for (final Class<?> clazz : componentDescriptor.getApiEndpoints()) {
            this.dispatcher.getDispatcher()
                           .getRegistry()
                           .removeRegistrations(clazz,
                                                calculateBasePath(reference));
        }

        this.bundleContext.ungetService(reference);
    }

    private static String calculateBasePath(final ServiceReference<ComponentDescriptor> reference) {
        return String.format("/api/%s/",
                             reference.getProperty("component"));
    }

    public HttpServletDispatcher getDispatcher() {
        return this.dispatcher;
    }
}
