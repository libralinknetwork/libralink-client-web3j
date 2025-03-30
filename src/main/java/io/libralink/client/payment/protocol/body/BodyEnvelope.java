package io.libralink.client.payment.protocol.body;

public class BodyEnvelope {

    private BodyContent body;

    private BodyEnvelope() {}

    public BodyContent getBody() {
        return body;
    }

    void setBody(BodyContent body) {
        this.body = body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private BodyContent body;

        private Builder() {}

        public Builder addBody(BodyContent body) {
            this.body = body;
            return this;
        }

        public BodyEnvelope build() {
            BodyEnvelope bodyEnvelope = new BodyEnvelope();
            bodyEnvelope.setBody(body);
            return bodyEnvelope;
        }
    }
}
