package io.libralink.client.payment.protocol.api.account;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class RegisterKeyRequest extends APIObject {

    private String pub;
    private String challenge;

    public String getPub() {
        return pub;
    }

    void setPub(String pub) {
        this.pub = pub;
    }

    public String getChallenge() {
        return challenge;
    }

    void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private String pub;
        private String challenge;

        private Builder() {}

        public Builder addPub(String pub) {
            this.pub = pub;
            return this;
        }

        public Builder addChallenge(String challenge) {
            this.challenge = challenge;
            return this;
        }

        public RegisterKeyRequest build() throws BuilderException {

            if (pub == null || challenge == null) {
                throw new BuilderException();
            }

            RegisterKeyRequest request = super.build(new RegisterKeyRequest());
            request.setPub(pub);
            request.setChallenge(challenge);
            return request;
        }
    }
}
