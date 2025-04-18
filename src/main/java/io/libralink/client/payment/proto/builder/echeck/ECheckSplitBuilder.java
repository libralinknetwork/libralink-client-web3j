package io.libralink.client.payment.proto.builder.echeck;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.math.BigDecimal;

public final class ECheckSplitBuilder {

    private BigDecimal amount;
    private String to;
    private String toProc;

    private ECheckSplitBuilder() {}

    public static ECheckSplitBuilder newBuilder() {
        return new ECheckSplitBuilder();
    }

    public ECheckSplitBuilder addTo(String to) {
        this.to = to;
        return this;
    }

    public ECheckSplitBuilder addToProc(String toProc) {
        this.toProc = toProc;
        return this;
    }

    public ECheckSplitBuilder addAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Libralink.ECheckSplit build() throws BuilderException {

        if (amount == null || to == null || toProc == null) {
            throw new BuilderException();
        }

        return Libralink.ECheckSplit.newBuilder()
                .setAmount(amount.toString())
                .setTo(to)
                .setToProc(toProc)
            .build();
    }
}
