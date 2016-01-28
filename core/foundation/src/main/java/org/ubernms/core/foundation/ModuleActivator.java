package org.ubernms.core.foundation;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

public abstract class ModuleActivator implements BundleActivator, Module {
    private Injector injector;

    @Override
    public final void start(final BundleContext context) throws Exception {
        // Ensure the context class loader is using the bundle hierarchy class loader
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

        // Create an injector for the bundle
        this.injector = Guice.createInjector((binder) -> {
            binder.requireAtInjectOnConstructors();
            binder.requireExactBindingAnnotations();

            // Export the context
            binder.bind(BundleContext.class).toInstance(context);
        }).createChildInjector(this);

        // Start the bundle using the injector
        this.start(injector);
    }

    protected abstract void start(final Injector injector);
    protected abstract void stop(final Injector injector);

    public final void configure(final Binder binder) {
    }

    @Override
    public final void stop(final BundleContext context) throws Exception {
        this.stop(this.injector);

        this.injector = null;
    }

}
