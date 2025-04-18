package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class GetProcessorRequestBuilder {

    private String address;

    private GetProcessorRequestBuilder() {}

    public static GetProcessorRequestBuilder newBuilder() {
        return new GetProcessorRequestBuilder();
    }

    public GetProcessorRequestBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public Libralink.GetProcessorsRequest build() throws BuilderException {

        if (address == null) {
            throw new BuilderException();
        }

        return Libralink.GetProcessorsRequest.newBuilder()
                .setAddress(address)
            .build();
    }
}
