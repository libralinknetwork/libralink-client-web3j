package io.libralink.client.payment.proto.builder.fee;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.math.BigDecimal;

public final class ProcessingFeeBuilder {

    private String feeType;
    private BigDecimal amount;
    private String intermediary;
    private Libralink.Envelope envelope;

    private ProcessingFeeBuilder() {}

    public static ProcessingFeeBuilder newBuilder() {
        return new ProcessingFeeBuilder();
    }

    public ProcessingFeeBuilder addEnvelope(Libralink.Envelope envelope) {
        this.envelope = envelope;
        return this;
    }

    public ProcessingFeeBuilder addIntermediary(String intermediary) {
        this.intermediary = intermediary;
        return this;
    }

    public ProcessingFeeBuilder addFeeType(String feeType) {
        this.feeType = feeType;
        return this;
    }

    public ProcessingFeeBuilder addAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Libralink.ProcessingFee build() throws BuilderException {
        if (feeType == null || amount == null || envelope == null) {
            throw new BuilderException();
        }

        Libralink.ProcessingFee.Builder builder = Libralink.ProcessingFee.newBuilder()
                .setFeeType(feeType)
                .setAmount(amount.toString())
                .setEnvelope(envelope);

        if (intermediary != null) builder.setIntermediary(intermediary);

        return builder.build();
    }
}
