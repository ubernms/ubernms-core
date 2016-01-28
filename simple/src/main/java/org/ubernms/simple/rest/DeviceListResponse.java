package org.ubernms.simple.rest;

import com.google.common.collect.Sets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

@XmlRootElement(name = "device-list")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceListResponse {

    @XmlType(name = "device")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Element {
        @XmlAttribute(name = "id")
        private UUID id;

        @XmlElement(name = "name")
        private String name;

        public UUID getId() {
            return this.id;
        }

        public void setId(final UUID id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

    @XmlElement(name = "device")
    @XmlElementWrapper(name = "devices")
    private Set<Element> elements = Sets.newHashSet();

    public Set<Element> getElements() {
        return this.elements;
    }

    public void setElements(final Set<Element> elements) {
        this.elements = elements;
    }
}
