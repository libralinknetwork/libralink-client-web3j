package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.math.BigDecimal;

public final class GetBalanceResponseBuilder {

    private String address;
    private BigDecimal available;
    private BigDecimal pending;

    private GetBalanceResponseBuilder() {}

    public static GetBalanceResponseBuilder newBuilder() {
        return new GetBalanceResponseBuilder();
    }

    public GetBalanceResponseBuilder addPending(BigDecimal pending) {
        this.pending = pending;
        return this;
    }

    public GetBalanceResponseBuilder addAvailable(BigDecimal available) {
        this.available = available;
        return this;
    }

    public GetBalanceResponseBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public Libralink.GetBalanceResponse build() throws BuilderException {

        if (address == null || available == null || pending == null) {
            throw new BuilderException();
        }

        return Libralink.GetBalanceResponse.newBuilder()
                .setAddress(address)
                .setAvailable(available.toString())
                .setPending(pending.toString())
            .build();
    }
}
