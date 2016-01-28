package org.ubernms.simple.impl.rest;

import org.ubernms.simple.impl.DeviceService;
import org.ubernms.simple.rest.DeviceListResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.UUID;

@Path("/devices")
public class DeviceResource {

    private final DeviceService deviceService;

    @Inject
    public DeviceResource(final DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GET
    public DeviceListResponse getDevices() throws Exception {
        final DeviceListResponse response = new DeviceListResponse();

        final DeviceListResponse.Element e1 = new DeviceListResponse.Element();
        e1.setId(UUID.randomUUID());
        e1.setName("foobar");
        response.getElements().add(e1);

        final DeviceListResponse.Element e2 = new DeviceListResponse.Element();
        e2.setId(UUID.randomUUID());
        e2.setName("scummbar");
        response.getElements().add(e2);

        final DeviceListResponse.Element e3 = new DeviceListResponse.Element();
        e3.setId(UUID.randomUUID());
        e3.setName("furchtbar");
        response.getElements().add(e3);

        return response;
    }

}
