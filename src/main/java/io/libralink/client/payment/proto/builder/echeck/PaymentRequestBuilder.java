package io.libralink.client.payment.proto.builder.echeck;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.math.BigDecimal;
import java.util.UUID;

public final class PaymentRequestBuilder {

    private BigDecimal amount;
    private String currency;
    private String from;
    private String fromProc;
    private String to;
    private String toProc;
    private long createdAt;
    private String note;
    private UUID correlationId;

    private PaymentRequestBuilder() {}

    public static PaymentRequestBuilder newBuilder() {
        return new PaymentRequestBuilder();
    }

    public PaymentRequestBuilder addCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public PaymentRequestBuilder addNote(String note) {
        this.note = note;
        return this;
    }

    public PaymentRequestBuilder addCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PaymentRequestBuilder addToProc(String toProc) {
        this.toProc = toProc;
        return this;
    }

    public PaymentRequestBuilder addTo(String to) {
        this.to = to;
        return this;
    }

    public PaymentRequestBuilder addFromProc(String fromProc) {
        this.fromProc = fromProc;
        return this;
    }

    public PaymentRequestBuilder addFrom(String from) {
        this.from = from;
        return this;
    }

    public PaymentRequestBuilder addCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public PaymentRequestBuilder addAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Libralink.PaymentRequest build() throws BuilderException {

        if (amount == null || correlationId == null || createdAt <= 0 || from == null || to == null || currency == null) {
            throw new BuilderException();
        }

        Libralink.PaymentRequest.Builder builder = Libralink.PaymentRequest.newBuilder()
            .setCreatedAt(createdAt)
            .setCorrelationId(correlationId.toString())
            .setCurrency(currency)
            .setFrom(from)
            .setFromProc(fromProc)
            .setTo(to)
            .setToProc(toProc)
            .setAmount(amount.toString());

        if (note != null) builder.setNote(note);

        return builder.build();
    }
}
