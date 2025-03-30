package io.libralink.client.payment.protocol.body;

import java.math.BigDecimal;

public class ECheckBody implements BodyContent {

    private final String objectType = ECheckBody.class.getSimpleName();
    private BigDecimal amount;
    private String type;
    private String payer;
    private String payerProcessor;
    private String payee;
    private String payeeProcessor;
    private long createdAt;
    private long expiresAt;
    private String note;

    private ECheckBody() {}

    public BigDecimal getAmount() {
        return amount;
    }

    void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public String getPayer() {
        return payer;
    }

    void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayerProcessor() {
        return payerProcessor;
    }

    void setPayerProcessor(String payerProcessor) {
        this.payerProcessor = payerProcessor;
    }

    public String getPayeeProcessor() {
        return payeeProcessor;
    }

    void setPayeeProcessor(String payeeProcessor) {
        this.payeeProcessor = payeeProcessor;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getNote() {
        return note;
    }

    void setNote(String note) {
        this.note = note;
    }

    public String getObjectType() {
        return objectType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private BigDecimal amount;
        private String type;
        private String payer;
        private String payerProcessor;
        private String payee;
        private String payeeProcessor;
        private long createdAt;
        private long expiresAt;
        private String note;

        private Builder() {}

        public Builder addNote(String note) {
            this.note = note;
            return this;
        }

        public Builder addCreatedAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder addExpiresAt(long expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder addPayee(String payee) {
            this.payee = payee;
            return this;
        }

        public Builder addPayeeProcessor(String payeeProcessor) {
            this.payeeProcessor = payeeProcessor;
            return this;
        }

        public Builder addPayer(String payer) {
            this.payer = payer;
            return this;
        }

        public Builder addPayerProcessor(String payerProcessor) {
            this.payerProcessor = payerProcessor;
            return this;
        }

        public Builder addType(String type) {
            this.type = type;
            return this;
        }

        public Builder addAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public ECheckBody build() {
            ECheckBody eCheckBody = new ECheckBody();
            eCheckBody.setAmount(amount);
            eCheckBody.setNote(note);
            eCheckBody.setType(type);
            eCheckBody.setPayee(payee);
            eCheckBody.setPayeeProcessor(payeeProcessor);
            eCheckBody.setPayer(payer);
            eCheckBody.setPayerProcessor(payerProcessor);
            eCheckBody.setCreatedAt(createdAt);
            eCheckBody.setExpiresAt(expiresAt);
            return eCheckBody;
        }
    }
}
