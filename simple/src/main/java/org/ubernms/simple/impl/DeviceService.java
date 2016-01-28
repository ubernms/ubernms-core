package org.ubernms.simple.impl;

import com.google.common.collect.ImmutableSet;
import org.ubernms.simple.impl.persistence.Device;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class DeviceService {

    @Inject
    public DeviceService() {
    }

    public Set<Device> getAllDevices() {
        return ImmutableSet.<Device>builder()
                .add(Device.builder()
                           .withName("foobar")
                           .build())
                .add(Device.builder()
                           .withName("scummbar")
                           .build())
                .add(Device.builder()
                           .withName("furchtbar")
                           .build())
                .build();
    }

}
