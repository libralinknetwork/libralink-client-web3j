package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class RegisterKeyRequestBuilder {

    private String address;
    private String pubKey;
    private String confirmationId;
    private String hash;
    private String algorithm;

    private RegisterKeyRequestBuilder() {}

    public static RegisterKeyRequestBuilder newBuilder() {
        return new RegisterKeyRequestBuilder();
    }

    public RegisterKeyRequestBuilder addAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public RegisterKeyRequestBuilder addHash(String hash) {
        this.hash = hash;
        return this;
    }

    public RegisterKeyRequestBuilder addConfirmationId(String confirmationId) {
        this.confirmationId = confirmationId;
        return this;
    }

    public RegisterKeyRequestBuilder addPubKey(String pubKey) {
        this.pubKey = pubKey;
        return this;
    }

    public RegisterKeyRequestBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public Libralink.RegisterKeyRequest build() throws BuilderException {

        if (address == null || confirmationId == null || hash == null) {
            throw new BuilderException();
        }

        if ("DILITHIUM5".equals(algorithm) && pubKey == null) {
            throw new BuilderException();
        }

        Libralink.RegisterKeyRequest.Builder builder = Libralink.RegisterKeyRequest.newBuilder()
                .setAddress(address)
                .setAlgorithm(algorithm)
                .setHash(hash)
                .setConfirmationId(confirmationId);

        if (pubKey != null) builder.setPubKey(pubKey);

        return builder.build();
    }
}
