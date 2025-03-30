package io.libralink.client.payment.protocol.error;

public class ErrorEnvelope {

    private ErrorMessage error;

    private ErrorEnvelope() {}

    public ErrorMessage getError() {
        return error;
    }

    void setError(ErrorMessage error) {
        this.error = error;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private ErrorMessage error;

        private Builder() {}

        public Builder addError(ErrorMessage error) {
            this.error = error;
            return this;
        }

        public ErrorEnvelope build() {
            ErrorEnvelope errorEnvelope = new ErrorEnvelope();
            errorEnvelope.setError(error);
            return errorEnvelope;
        }
    }
}
