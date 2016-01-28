package org.ubernms.simple.impl.persistence;

import java.util.UUID;

public class Device {

    private final UUID id;

    private String name;

    public Device(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public static class Builder {
        private final Device device;

        private Builder() {
            this.device = new Device(UUID.randomUUID());
        }

        public Builder withName(final String name) {
            this.device.setName(name);
            return this;
        }

        public Device build() {
            return this.device;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
