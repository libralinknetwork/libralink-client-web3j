package io.libralink.client.payment.protocol.echeck;

import io.libralink.client.payment.protocol.BaseEntity;
import io.libralink.client.payment.protocol.exception.BuilderException;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositApproval extends BaseEntity {

    private UUID checkId;
    private BigDecimal amount;
    private long createdAt;
    private String payer;
    private String payee;
    private String note;

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

    public String getNote() {
        return note;
    }

    void setNote(String note) {
        this.note = note;
    }

    public UUID getCheckId() {
        return checkId;
    }

    void setCheckId(UUID checkId) {
        this.checkId = checkId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends BaseEntityBuilder {

        private UUID checkId;
        private BigDecimal amount;
        private long createdAt;
        private String payer;
        private String payee;
        private String note;

        private Builder() {}

        public Builder addNote(String note) {
            this.note = note;
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

        public Builder addCheckId(UUID checkId) {
            this.checkId = checkId;
            return this;
        }

        public Builder addAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder addCreatedAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DepositApproval build() throws BuilderException {

            if (amount == null || checkId == null || createdAt <= 0 || payee == null || payer == null) {
                throw new BuilderException();
            }

            DepositApproval approval = super.build(new DepositApproval());
            approval.setAmount(amount);
            approval.setCheckId(checkId);
            approval.setCreatedAt(createdAt);
            approval.setPayee(payee);
            approval.setPayer(payer);
            approval.setNote(note);
            return approval;
        }
    }
}
