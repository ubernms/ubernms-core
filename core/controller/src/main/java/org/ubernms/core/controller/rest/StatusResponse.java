package org.ubernms.core.controller.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusResponse {

    public enum OverallStatus {
        OK,
        DEGRADED,
        FAILED,
    }

    @XmlElement
    private OverallStatus overallStatus;

    public OverallStatus getOverallStatus() {
        return this.overallStatus;
    }

    public void setOverallStatus(final OverallStatus overallStatus) {
        this.overallStatus = overallStatus;
    }
}
