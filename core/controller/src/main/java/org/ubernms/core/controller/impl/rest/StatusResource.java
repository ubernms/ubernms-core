package org.ubernms.core.controller.impl.rest;

import org.ubernms.core.controller.rest.StatusResponse;

//import javax.ws.rs.GET;
//import javax.ws.rs.Path;

//@Path("/status")
public class StatusResource {

//    @GET
    public StatusResponse getStatus() {
        final StatusResponse response = new StatusResponse();
        response.setOverallStatus(StatusResponse.OverallStatus.OK);

        return response;
    }
}
