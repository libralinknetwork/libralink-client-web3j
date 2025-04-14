package io.libralink.client.payment.protocol.api.account;

import io.libralink.client.payment.protocol.SignatureAlgorithm;
import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class RegisterKeyRequest extends APIObject {

    private String address;
    private String pubKey;
    private String confirmationId;
    private String hash;
    private SignatureAlgorithm algorithm;

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    void setConfirmationId(String confirmationId) {
        this.confirmationId = confirmationId;
    }

    public String getHash() {
        return hash;
    }

    void setHash(String hash) {
        this.hash = hash;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    void setAlgorithm(SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getPubKey() {
        return pubKey;
    }

    void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private String address;
        private String pubKey;
        private String confirmationId;
        private String hash;
        private SignatureAlgorithm algorithm;

        private Builder() {}

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

        public Builder addConfirmationId(String confirmationId) {
            this.confirmationId = confirmationId;
            return this;
        }

        public Builder addHash(String hash) {
            this.hash = hash;
            return this;
        }

        public RegisterKeyRequest build() throws BuilderException {

            if (address == null || confirmationId == null || hash == null) {
                throw new BuilderException();
            }

            if (SignatureAlgorithm.DILITHIUM5.equals(algorithm) && pubKey == null) {
                throw new BuilderException();
            }

            RegisterKeyRequest request = super.build(new RegisterKeyRequest());
            request.setAddress(address);
            request.setPubKey(pubKey);
            request.setConfirmationId(confirmationId);
            request.setHash(hash);
            request.setAlgorithm(algorithm);
            return request;
        }
    }
}
