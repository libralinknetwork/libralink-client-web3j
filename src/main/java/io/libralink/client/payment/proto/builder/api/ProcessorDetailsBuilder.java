package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class ProcessorDetailsBuilder {

    private String address;

    private ProcessorDetailsBuilder() {}

    public static ProcessorDetailsBuilder newBuilder() {
        return new ProcessorDetailsBuilder();
    }

    public ProcessorDetailsBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public Libralink.ProcessorDetails build() throws BuilderException {

        if (address == null) {
            throw new BuilderException();
        }

        return Libralink.ProcessorDetails.newBuilder()
                .setAddress(address)
            .build();
    }
}
