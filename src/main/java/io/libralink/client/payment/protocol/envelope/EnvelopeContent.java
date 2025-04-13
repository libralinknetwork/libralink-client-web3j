package io.libralink.client.payment.protocol.envelope;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.SignatureAlgorithm;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class EnvelopeContent {

    private AbstractEntity entity;
    private String address;
    private String pubKey;
    private SignatureReason sigReason;
    private SignatureAlgorithm algorithm;

    public AbstractEntity getEntity() {
        return entity;
    }

    void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public SignatureReason getSigReason() {
        return sigReason;
    }

    void setSigReason(SignatureReason sigReason) {
        this.sigReason = sigReason;
    }

    public String getPubKey() {
        return pubKey;
    }

    void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    void setAlgorithm(SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(EnvelopeContent content) {
        return new Builder(content);
    }

    public static class Builder {

        private AbstractEntity entity;
        private String address;
        private String pubKey;
        private SignatureReason sigReason;
        private SignatureAlgorithm algorithm;

        private Builder() {}

        private Builder(EnvelopeContent content) {
            this.entity = content.entity;
            this.address = content.address;
            this.pubKey = content.pubKey;
            this.sigReason = content.sigReason;
            this.algorithm = content.algorithm;
        }

        public Builder addEntity(AbstractEntity entity) {
            this.entity = entity;
            return this;
        }

        public Builder addAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder addAlgorithm(SignatureAlgorithm algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        public Builder addPubKey(String pubKey) {
            this.pubKey = pubKey;
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

            if (SignatureAlgorithm.DILITHIUM5.equals(algorithm) && pubKey == null) {
                throw new BuilderException();
            }

            EnvelopeContent envelope = new EnvelopeContent();
            envelope.setEntity(entity);
            envelope.setSigReason(sigReason);
            envelope.setAddress(address);
            envelope.setPubKey(pubKey);
            envelope.setAlgorithm(algorithm);
            return envelope;
        }
    }
}
