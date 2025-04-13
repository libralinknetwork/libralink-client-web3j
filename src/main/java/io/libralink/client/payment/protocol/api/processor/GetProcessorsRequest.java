package io.libralink.client.payment.protocol.api.processor;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class GetProcessorsRequest extends APIObject {

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

        public GetProcessorsRequest build() throws BuilderException {

            if (address == null) {
                throw new BuilderException();
            }

            GetProcessorsRequest request = super.build(new GetProcessorsRequest());
            request.setAddress(address);
            return request;
        }
    }
}
