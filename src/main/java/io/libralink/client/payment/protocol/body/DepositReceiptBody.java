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
    private String payerParty;
    private String payee;
    private String payeeParty;
    private long createdAt;
    private UUID checkId;
    private List<UUID> requestIds = new ArrayList<>();

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

    public String getPayerParty() {
        return payerParty;
    }

    void setPayerParty(String payerParty) {
        this.payerParty = payerParty;
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

    public UUID getCheckId() {
        return checkId;
    }

    void setCheckId(UUID checkId) {
        this.checkId = checkId;
    }

    public List<UUID> getRequestIds() {
        return requestIds;
    }

    void setRequestIds(List<UUID> requestIds) {
        this.requestIds = requestIds;
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
        private UUID checkId;
        private List<UUID> requestIds;

        private Builder() {}

        public Builder addPaymentRequestEnvelopeIds(List<UUID> requestIds) {
            this.requestIds = requestIds;
            return this;
        }

        public Builder addECheckEnvelopeId(UUID checkId) {
            this.checkId = checkId;
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

        public DepositReceiptBody build() {
            DepositReceiptBody depositReceiptBody = new DepositReceiptBody();
            depositReceiptBody.setAmount(amount);
            depositReceiptBody.setCreatedAt(createdAt);
            depositReceiptBody.setPayee(payee);
            depositReceiptBody.setPayeeParty(payeeParty);
            depositReceiptBody.setPayer(payer);
            depositReceiptBody.setPayerParty(payerParty);
            depositReceiptBody.setType(type);
            depositReceiptBody.setCheckId(checkId);
            depositReceiptBody.setRequestIds(requestIds);
            return depositReceiptBody;
        }
    }
}

