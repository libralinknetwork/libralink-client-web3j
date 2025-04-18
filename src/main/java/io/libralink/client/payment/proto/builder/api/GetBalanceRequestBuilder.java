package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class GetBalanceRequestBuilder {

    private String address;

    private GetBalanceRequestBuilder() {}

    public static GetBalanceRequestBuilder newBuilder() {
        return new GetBalanceRequestBuilder();
    }

    public GetBalanceRequestBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public Libralink.GetBalanceRequest build() throws BuilderException {

        if (address == null) {
            throw new BuilderException();
        }

        return Libralink.GetBalanceRequest.newBuilder()
                .setAddress(address)
            .build();
    }
}
