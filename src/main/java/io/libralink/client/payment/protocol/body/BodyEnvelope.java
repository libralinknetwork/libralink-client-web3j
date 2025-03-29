package io.libralink.client.payment.protocol.body;

public class BodyEnvelope {

    private BodyContent content;

    private BodyEnvelope() {}

    public BodyContent getContent() {
        return content;
    }

    void setContent(BodyContent content) {
        this.content = content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private BodyContent content;

        private Builder() {}

        public Builder addContent(BodyContent content) {
            this.content = content;
            return this;
        }

        public BodyEnvelope build() {
            BodyEnvelope bodyEnvelope = new BodyEnvelope();
            bodyEnvelope.setContent(content);
            return bodyEnvelope;
        }
    }
}
