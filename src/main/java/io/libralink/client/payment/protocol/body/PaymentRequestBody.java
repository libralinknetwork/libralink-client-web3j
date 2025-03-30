package io.libralink.client.payment.protocol.body;

import java.math.BigDecimal;

public class PaymentRequestBody implements BodyContent {

    private final String objectType = PaymentRequestBody.class.getSimpleName();
    private BigDecimal amount;
    private String type;
    private String payer;
    private String payerParty;
    private String payee;
    private String payeeParty;
    private long createdAt;
    private String note;

    private PaymentRequestBody() {}

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

    public String getPayerParty() {
        return payerParty;
    }

    void setPayerParty(String payerParty) {
        this.payerParty = payerParty;
    }

    public String getPayee() {
        return payee;
    }

    void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayeeParty() {
        return payeeParty;
    }

    void setPayeeParty(String payeeParty) {
        this.payeeParty = payeeParty;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
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
        private String payerParty;
        private String payee;
        private String payeeParty;
        private long createdAt;
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

        public Builder addPayee(String payee) {
            this.payee = payee;
            return this;
        }

        public Builder addPayeeParty(String payeeParty) {
            this.payeeParty = payeeParty;
            return this;
        }

        public Builder addPayer(String payer) {
            this.payer = payer;
            return this;
        }

        public Builder addPayerParty(String payerParty) {
            this.payerParty = payerParty;
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

        public PaymentRequestBody build() {
            PaymentRequestBody paymentRequestBody = new PaymentRequestBody();
            paymentRequestBody.setAmount(amount);
            paymentRequestBody.setNote(note);
            paymentRequestBody.setType(type);
            paymentRequestBody.setPayee(payee);
            paymentRequestBody.setPayeeParty(payeeParty);
            paymentRequestBody.setPayer(payer);
            paymentRequestBody.setPayerParty(payerParty);
            paymentRequestBody.setCreatedAt(createdAt);
            return paymentRequestBody;
        }
    }
}
