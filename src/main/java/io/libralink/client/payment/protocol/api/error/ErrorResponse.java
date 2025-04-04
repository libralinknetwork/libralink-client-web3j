package io.libralink.client.payment.protocol.api.error;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class ErrorResponse extends APIObject {

    private int code;
    private String message;

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    void setCode(int code) {
        this.code = code;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private int code;
        private String message;

        private Builder() {}

        public Builder addCode(int code) {
            this.code = code;
            return this;
        }

        public Builder addMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() throws BuilderException {

            if (code < 0 || message == null) {
                throw new BuilderException();
            }

            ErrorResponse response = super.build(new ErrorResponse());
            response.setMessage(message);
            response.setCode(code);
            return response;
        }
    }
}
