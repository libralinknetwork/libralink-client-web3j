package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class ErrorResponseBuilder {

    private int code;
    private String message;

    public static ErrorResponseBuilder newBuilder() {
        return new ErrorResponseBuilder();
    }

    public ErrorResponseBuilder addMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponseBuilder addCode(int code) {
        this.code = code;
        return this;
    }

    public Libralink.ErrorResponse build() throws BuilderException {

        if (code < 0 || message == null) {
            throw new BuilderException();
        }

        return Libralink.ErrorResponse.newBuilder()
                .setCode(code)
                .setMessage(message)
            .build();
    }
}
