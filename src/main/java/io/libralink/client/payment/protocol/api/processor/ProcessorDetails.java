package io.libralink.client.payment.protocol.api.processor;

import io.libralink.client.payment.protocol.exception.BuilderException;

public class ProcessorDetails {

    private String pub;
    private Boolean isDefault;

    public String getPub() {
        return pub;
    }

    void setPub(String pub) {
        this.pub = pub;
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

        private String pub;
        private Boolean isDefault;

        private Builder() {}

        public Builder addPub(String pub) {
            this.pub = pub;
            return this;
        }

        public Builder addDefault(Boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public ProcessorDetails build() throws BuilderException {

            if (pub == null || isDefault == null) {
                throw new BuilderException();
            }

            ProcessorDetails trustedProcessor = new ProcessorDetails();
            trustedProcessor.setPub(pub);
            trustedProcessor.setDefault(isDefault);
            return trustedProcessor;
        }
    }
}
