package io.libralink.client.payment.protocol.api.balance;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class GetBalanceRequest extends APIObject {

    private String pub;

    public String getPub() {
        return pub;
    }

    void setPub(String pub) {
        this.pub = pub;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private String pub;

        private Builder() {}

        public Builder addPub(String pub) {
            this.pub = pub;
            return this;
        }

        public GetBalanceRequest build() throws BuilderException {

            if (pub == null) {
                throw new BuilderException();
            }

            GetBalanceRequest request = super.build(new GetBalanceRequest());
            request.setPub(pub);
            return request;
        }
    }
}
