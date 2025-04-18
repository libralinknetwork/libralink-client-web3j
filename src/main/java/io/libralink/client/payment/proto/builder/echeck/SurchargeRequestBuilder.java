package io.libralink.client.payment.proto.builder.echeck;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.math.BigDecimal;

public final class SurchargeRequestBuilder {

    private BigDecimal amount;
    private String to;
    private String toProc;
    private String note;
    private Libralink.Envelope envelope;

    private SurchargeRequestBuilder() {}

    public static SurchargeRequestBuilder newBuilder() {
        return new SurchargeRequestBuilder();
    }

    public SurchargeRequestBuilder addEnvelope(Libralink.Envelope envelope) {
        this.envelope = envelope;
        return this;
    }

    public SurchargeRequestBuilder addNote(String note) {
        this.note = note;
        return this;
    }

    public SurchargeRequestBuilder addToProc(String toProc) {
        this.toProc = toProc;
        return this;
    }

    public SurchargeRequestBuilder addTo(String to) {
        this.to = to;
        return this;
    }

    public SurchargeRequestBuilder addAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Libralink.SurchargeRequest build() throws BuilderException {

        if (amount == null || to == null || toProc == null || envelope == null) {
            throw new BuilderException();
        }

        return Libralink.SurchargeRequest.newBuilder()
            .setEnvelope(envelope)
            .setAmount(amount.toString())
            .setTo(to)
            .setToProc(toProc)
            .setNote(note)
            .build();
    }
}
