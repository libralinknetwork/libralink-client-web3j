package io.libralink.client.payment.protocol.echeck;

import io.libralink.client.payment.protocol.BaseEntity;
import io.libralink.client.payment.protocol.exception.BuilderException;

import java.math.BigDecimal;

public class ECheck extends BaseEntity {

    private BigDecimal faceAmount;
    private String currency;
    private String payer;
    private String payerProcessor;
    private String payee;
    private String payeeProcessor;
    private long createdAt;
    private long expiresAt;
    private String note;

    public BigDecimal getFaceAmount() {
        return faceAmount;
    }

    void setFaceAmount(BigDecimal faceAmount) {
        this.faceAmount = faceAmount;
    }

    public String getCurrency() {
        return currency;
    }

    void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayer() {
        return payer;
    }

    void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerProcessor() {
        return payerProcessor;
    }

    void setPayerProcessor(String payerProcessor) {
        this.payerProcessor = payerProcessor;
    }

    public String getPayee() {
        return payee;
    }

    void setPayee(String payee) {
        this.payee = payee;
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

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends BaseEntityBuilder {

        private BigDecimal faceAmount;
        private String currency;
        private String payer;
        private String payerProcessor;
        private String payee;
        private String payeeProcessor;
        private long createdAt;
        private long expiresAt;
        private String note;

        private Builder() {
            super();
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

        public Builder addFaceAmount(BigDecimal faceAmount) {
            this.faceAmount = faceAmount;
            return this;
        }

        public Builder addCurrency(String currency) {
            this.currency = currency;
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

        public Builder addNote(String note) {
            this.note = note;
            return this;
        }

        public ECheck build() throws BuilderException {

            if (faceAmount == null || currency == null || payer == null || payerProcessor == null ||
                    payee == null || payeeProcessor == null) {
                throw new BuilderException();
            }

            if (createdAt <= 0 || expiresAt <= 0 || createdAt > expiresAt) {
                throw new BuilderException();
            }

            ECheck echeck = super.build(new ECheck());
            echeck.setFaceAmount(faceAmount);
            echeck.setCreatedAt(createdAt);
            echeck.setExpiresAt(expiresAt);
            echeck.setNote(note);
            echeck.setCurrency(currency);
            echeck.setPayer(payer);
            echeck.setPayerProcessor(payerProcessor);
            echeck.setPayee(payee);
            echeck.setPayeeProcessor(payeeProcessor);
            return echeck;
        }
    }
}
