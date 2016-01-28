package org.ubernms.core.controller.spi;

import com.google.inject.Module;

import java.util.Set;

public interface ComponentDescriptor {
    String getId();

    Module getModule();

    Set<Class<?>> getApiEndpoints();
}
