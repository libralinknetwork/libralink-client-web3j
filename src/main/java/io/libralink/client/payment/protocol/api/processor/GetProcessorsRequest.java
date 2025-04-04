package io.libralink.client.payment.protocol.api.processor;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class GetProcessorsRequest extends APIObject {

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

        public GetProcessorsRequest build() throws BuilderException {

            if (pub == null) {
                throw new BuilderException();
            }

            GetProcessorsRequest request = super.build(new GetProcessorsRequest());
            request.setPub(pub);
            return request;
        }
    }
}
