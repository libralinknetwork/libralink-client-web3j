package io.libralink.client.payment.proto.builder.echeck;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public final class ECheckBuilder {

    private BigDecimal faceAmount;
    private String currency;
    private String from;
    private String fromProc;
    private String to;
    private String toProc;
    private long createdAt;
    private long expiresAt;
    private String note;
    private UUID correlationId;
    private List<Libralink.ECheckSplit> splits;

    private ECheckBuilder() {}

    public static ECheckBuilder newBuilder() {
        return new ECheckBuilder();
    }

    public ECheckBuilder addSplits(List<Libralink.ECheckSplit> splits) {
        this.splits = splits;
        return this;
    }

    public ECheckBuilder addCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public ECheckBuilder addExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public ECheckBuilder addCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ECheckBuilder addNote(String note) {
        this.note = note;
        return this;
    }

    public ECheckBuilder addCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ECheckBuilder addFrom(String from) {
        this.from = from;
        return this;
    }

    public ECheckBuilder addFromProc(String fromProc) {
        this.fromProc = fromProc;
        return this;
    }

    public ECheckBuilder addTo(String to) {
        this.to = to;
        return this;
    }

    public ECheckBuilder addToProc(String toProc) {
        this.toProc = toProc;
        return this;
    }

    public ECheckBuilder addFaceAmount(BigDecimal faceAmount) {
        this.faceAmount = faceAmount;
        return this;
    }

    public Libralink.ECheck build() throws BuilderException {

        if (faceAmount == null || currency == null || from == null || fromProc == null ||
                to == null || toProc == null || correlationId == null || splits == null || splits.isEmpty()) {
            throw new BuilderException();
        }

        if (createdAt <= 0 || expiresAt <= 0 || createdAt > expiresAt) {
            throw new BuilderException();
        }

        Libralink.ECheck.Builder builder = Libralink.ECheck.newBuilder()
            .setCreatedAt(createdAt)
            .setExpiresAt(expiresAt)
            .setCorrelationId(correlationId.toString())
            .setCurrency(currency)
            .setFrom(from)
            .setFromProc(fromProc)
            .setTo(to)
            .setToProc(toProc)
            .setFaceAmount(faceAmount.toString());

        if (note != null) builder.setNote(note);

        for (int i=0; i<splits.size(); i++) {
            builder.addSplits(splits.get(i));
        }

        return builder.build();
    }
}
