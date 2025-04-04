package io.libralink.client.payment.protocol.processing;

import io.libralink.client.payment.protocol.exception.BuilderException;

import java.math.BigDecimal;

public class ProcessingFee {

    private String feeType;
    private BigDecimal amount;

    public String getFeeType() {
        return feeType;
    }

    void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private String feeType;
        private BigDecimal amount;

        private Builder() {}

        public Builder addFeeType(String feeType) {
            this.feeType = feeType;
            return this;
        }

        public Builder addAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public ProcessingFee build() throws BuilderException {

            if (feeType == null || amount == null) {
                throw new BuilderException();
            }

            ProcessingFee processingFee = new ProcessingFee();
            processingFee.setFeeType(feeType);
            processingFee.setAmount(amount);
            return processingFee;
        }
    }
}
