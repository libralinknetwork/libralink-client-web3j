package io.libralink.client.payment.protocol.api.account;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class RegisterKeyResponse extends APIObject {

    private String address;

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private String address;

        private Builder() {}

        public Builder addAddress(String address) {
            this.address = address;
            return this;
        }

        public RegisterKeyResponse build() throws BuilderException {

            if (address == null) {
                throw new BuilderException();
            }

            RegisterKeyResponse response = super.build(new RegisterKeyResponse());
            response.setAddress(address);
            return response;
        }
    }
}
