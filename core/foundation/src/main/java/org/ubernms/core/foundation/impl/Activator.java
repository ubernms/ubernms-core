package org.ubernms.core.foundation.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Activator implements BundleActivator {
    private final static Logger LOG = LoggerFactory.getLogger(Activator.class);

    @Override
    public void start(final BundleContext context) throws Exception {
        LOG.trace("Starting foundation");

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        LOG.trace("Stopping foundation");
    }
}
