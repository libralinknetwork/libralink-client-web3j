package io.libralink.client.payment.protocol.body;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepositReceiptBody implements BodyContent {

    private final String objectType = DepositReceiptBody.class.getSimpleName();
    private BigDecimal amount;
    private String type;
    private String payer;
    private String payee;
    private long createdAt;
    private UUID eCheckEnvelopeId;
    private List<UUID> paymentRequestEnvelopeIds = new ArrayList<>();

    private DepositReceiptBody() {}

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

    public long getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public UUID geteCheckEnvelopeId() {
        return eCheckEnvelopeId;
    }

    void seteCheckEnvelopeId(UUID eCheckEnvelopeId) {
        this.eCheckEnvelopeId = eCheckEnvelopeId;
    }

    public List<UUID> getPaymentRequestEnvelopeIds() {
        return paymentRequestEnvelopeIds;
    }

    void setPaymentRequestEnvelopeIds(List<UUID> paymentRequestEnvelopeIds) {
        this.paymentRequestEnvelopeIds = paymentRequestEnvelopeIds;
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
        private String payee;
        private long createdAt;
        private UUID eCheckEnvelopeId;
        private List<UUID> paymentRequestEnvelopeIds;

        private Builder() {}

        public Builder addPaymentRequestEnvelopeIds(List<UUID> paymentRequestEnvelopeIds) {
            this.paymentRequestEnvelopeIds = paymentRequestEnvelopeIds;
            return this;
        }

        public Builder addECheckEnvelopeId(UUID eCheckEnvelopeId) {
            this.eCheckEnvelopeId = eCheckEnvelopeId;
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

        public Builder addPayer(String payer) {
            this.payer = payer;
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

        public DepositReceiptBody build() {
            DepositReceiptBody depositReceiptBody = new DepositReceiptBody();
            depositReceiptBody.setAmount(amount);
            depositReceiptBody.setCreatedAt(createdAt);
            depositReceiptBody.setPayee(payee);
            depositReceiptBody.setPayer(payer);
            depositReceiptBody.setType(type);
            depositReceiptBody.seteCheckEnvelopeId(eCheckEnvelopeId);
            depositReceiptBody.setPaymentRequestEnvelopeIds(paymentRequestEnvelopeIds);
            return depositReceiptBody;
        }
    }
}

