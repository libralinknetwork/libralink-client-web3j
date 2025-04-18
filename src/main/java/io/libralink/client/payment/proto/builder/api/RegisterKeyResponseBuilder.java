package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class RegisterKeyResponseBuilder {

    private String address;

    private RegisterKeyResponseBuilder() {}

    public static RegisterKeyResponseBuilder newBuilder() {
        return new RegisterKeyResponseBuilder();
    }

    public RegisterKeyResponseBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public Libralink.RegisterKeyResponse build() throws BuilderException {

        if (address == null) {
            throw new BuilderException();
        }

        return Libralink.RegisterKeyResponse.newBuilder()
                .setAddress(address)
            .build();
    }
}
