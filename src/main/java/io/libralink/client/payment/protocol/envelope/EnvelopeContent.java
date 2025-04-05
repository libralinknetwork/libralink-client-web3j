package io.libralink.client.payment.protocol.envelope;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class EnvelopeContent {

    private AbstractEntity entity;
    private String pub;
    private SignatureReason sigReason;

    public AbstractEntity getEntity() {
        return entity;
    }

    void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    public String getPub() {
        return pub;
    }

    void setPub(String pub) {
        this.pub = pub;
    }

    public SignatureReason getSigReason() {
        return sigReason;
    }

    void setSigReason(SignatureReason sigReason) {
        this.sigReason = sigReason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(EnvelopeContent content) {
        return new Builder(content);
    }

    public static class Builder {

        private AbstractEntity entity;
        private String pub;
        private SignatureReason sigReason;

        private Builder() {}

        private Builder(EnvelopeContent content) {
            this.entity = content.entity;
            this.pub = content.pub;
            this.sigReason = content.sigReason;
        }

        public Builder addEntity(AbstractEntity entity) {
            this.entity = entity;
            return this;
        }

        public Builder addPub(String pub) {
            this.pub = pub;
            return this;
        }

        public Builder addSigReason(SignatureReason sigReason) {
            this.sigReason = sigReason;
            return this;
        }

        public EnvelopeContent build() throws BuilderException {

            if (entity == null) {
                throw new BuilderException();
            }

            EnvelopeContent envelope = new EnvelopeContent();
            envelope.setEntity(entity);
            envelope.setSigReason(sigReason);
            envelope.setPub(pub);
            return envelope;
        }
    }
}
