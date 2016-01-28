package org.ubernms.simple.impl;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import org.ubernms.core.controller.spi.ComponentActivator;
import org.ubernms.simple.impl.rest.DeviceResource;

import java.util.Set;

public class Activator extends ComponentActivator {

    @Override
    protected String getComponentId() {
        return "simple";
    }

    @Override
    protected Set<Class<?>> getApiEndpoints() {
        return ImmutableSet.<Class<?>>builder()
                .add(DeviceResource.class)
                .build();
    }
}
