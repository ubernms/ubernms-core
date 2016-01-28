package org.ubernms.core.controller.impl;

import com.google.common.base.Throwables;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ubernms.core.foundation.ModuleActivator;

import javax.inject.Inject;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class Activator extends ModuleActivator {
    private final static Logger LOG = LoggerFactory.getLogger(Activator.class);

    @Override
    protected void start(final Injector injector) {
        // Start API endpoint tracker
        injector.getInstance(ApiEndpointTracker.class).open();

        // Start the HTTP server
        LOG.trace("Starting HTTP server...");
        try {
            injector.getInstance(HttpServer.class).start();

        } catch (final IOException e) {
            throw Throwables.propagate(e);
        }

        LOG.trace("Controller up and running");
    }

    @Override
    protected void stop(final Injector injector) {
        // Stop the HTTP server
        LOG.trace("Stopping HTTP server");
        injector.getInstance(HttpServer.class).shutdownNow();

        // Stop API endpoint tracker
        injector.getInstance(ApiEndpointTracker.class).close();

        LOG.trace("Controller shut down - by by");
    }

    @Provides
    @Singleton
    @Inject
    private ApiEndpointTracker createApiEndpointTracker(final BundleContext bundleContext,
                                                        final Injector injector) {
        final HttpServletDispatcher dispatcher = new HttpServlet30Dispatcher();

        return new ApiEndpointTracker(bundleContext,
                                      injector,
                                      dispatcher);
    }

    @Provides
    @Singleton
    @Named("api")
    @Inject
    private WebappContext createApiContext(final ApiEndpointTracker apiEndpointTracker) {
        final WebappContext context = new WebappContext("ubernms-core-controller-api", "/api");

        final ServletRegistration registration = context.addServlet("ubernms-core-controller-api", apiEndpointTracker.getDispatcher());
        registration.setAsyncSupported(true);
        registration.setLoadOnStartup(1);
        registration.setInitParameter("resteasy.logger.type", "SLF4J");
        registration.setInitParameter("resteasy.servlet.mapping.prefix", "/api");
        registration.addMapping("/api/*");

        return context;
    }

    @Provides
    @Singleton
    @Named("dashboard")
    private WebappContext createDashboardContext() {
        final WebappContext context = new WebappContext("ubernms-core-controller-dashboard", "/dashboard");

        final ServletRegistration registration = context.addServlet("ubernms-core-controller-dashboard", new GenericServlet() {
            @Override
            public void service(final ServletRequest req,
                                final ServletResponse res) throws ServletException, IOException {
                res.setContentType("text/html");
                res.getWriter().write("The dashboard");
            }
        });
        registration.addMapping("/dashboard/*");

        return context;
    }

    @Provides
    @Singleton
    private HttpServer createHttpServer(@Named("api") final WebappContext apiContext,
                                        @Named("dashboard") final WebappContext dashboardContext) {
        LOG.trace("Create HTTP server");

        // Create and configure the HTTP server
        final HttpServer httpServer = new HttpServer();
        httpServer.addListener(new NetworkListener("ubernms",
                                                   "localhost",
                                                   8080));

        // Deploy the web apps
        apiContext.deploy(httpServer);
        dashboardContext.deploy(httpServer);

        return httpServer;
    }
}
