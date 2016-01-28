package org.ubernms.core.controller.spi;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.ubernms.core.foundation.ModuleActivator;

import java.util.Hashtable;
import java.util.Set;

public abstract class ComponentActivator extends ModuleActivator {

    private ServiceRegistration<ComponentDescriptor> moduleRegistration;

    @Override
    protected void start(final Injector injector) {
        final BundleContext bundleContext = injector.getInstance(BundleContext.class);

        // Create a component descriptor for this activator implementation
        final ComponentDescriptor componentDescriptor = new ComponentDescriptor() {
            @Override
            public String getId() {
                return ComponentActivator.this.getComponentId();
            }

            @Override
            public Module getModule() {
                return ComponentActivator.this;
            }

            @Override
            public Set<Class<?>> getApiEndpoints() {
                return ComponentActivator.this.getApiEndpoints();
            }
        };

        // Export the component descriptor
        this.moduleRegistration = bundleContext.registerService(ComponentDescriptor.class,
                                                                componentDescriptor,
                                                                new Hashtable<String, Object>() {{
                                                                    this.put("component", componentDescriptor.getId());
                                                                }});
    }

    @Override
    protected void stop(final Injector injector) {
        this.moduleRegistration.unregister();
    }

    protected abstract String getComponentId();

    protected abstract Set<Class<?>> getApiEndpoints();
}
