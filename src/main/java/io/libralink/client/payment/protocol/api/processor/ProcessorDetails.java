package io.libralink.client.payment.protocol.api.processor;

import io.libralink.client.payment.protocol.exception.BuilderException;

public class ProcessorDetails {

    private String address;
    private Boolean isDefault;

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private String address;
        private Boolean isDefault;

        private Builder() {}

        public Builder addAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder addDefault(Boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public ProcessorDetails build() throws BuilderException {

            if (address == null || isDefault == null) {
                throw new BuilderException();
            }

            ProcessorDetails trustedProcessor = new ProcessorDetails();
            trustedProcessor.setAddress(address);
            trustedProcessor.setDefault(isDefault);
            return trustedProcessor;
        }
    }
}
