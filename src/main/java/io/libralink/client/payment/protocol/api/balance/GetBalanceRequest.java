package io.libralink.client.payment.protocol.api.balance;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class GetBalanceRequest extends APIObject {

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

        public GetBalanceRequest build() throws BuilderException {

            if (address == null) {
                throw new BuilderException();
            }

            GetBalanceRequest request = super.build(new GetBalanceRequest());
            request.setAddress(address);
            return request;
        }
    }
}
