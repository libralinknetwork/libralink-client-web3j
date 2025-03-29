package io.libralink.client.payment.protocol.error;

public class ErrorEnvelope {

    private ErrorMessage content;

    private ErrorEnvelope() {}

    public ErrorMessage getContent() {
        return content;
    }

    void setContent(ErrorMessage content) {
        this.content = content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private ErrorMessage content;

        private Builder() {}

        public Builder addContent(ErrorMessage content) {
            this.content = content;
            return this;
        }

        public ErrorEnvelope build() {
            ErrorEnvelope errorEnvelope = new ErrorEnvelope();
            errorEnvelope.setContent(content);
            return errorEnvelope;
        }
    }
}
