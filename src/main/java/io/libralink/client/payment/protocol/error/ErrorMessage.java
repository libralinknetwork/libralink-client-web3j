package io.libralink.client.payment.protocol.error;

public class ErrorMessage {

    private int code;
    private String message;

    private ErrorMessage() {}

    public int getCode() {
        return code;
    }

    void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

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

        public ErrorMessage build() {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setCode(code);
            errorMessage.setMessage(message);
            return errorMessage;
        }
    }
}
