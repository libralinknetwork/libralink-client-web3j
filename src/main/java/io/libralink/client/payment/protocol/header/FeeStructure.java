package io.libralink.client.payment.protocol.header;

import java.math.BigDecimal;

public class FeeStructure {

    private BigDecimal fee;

    private FeeStructure() {}

    public BigDecimal getFee() {
        return fee;
    }

    void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private BigDecimal fee;

        private Builder() {}

        public Builder addFee(BigDecimal fee) {
            this.fee = fee;
            return this;
        }

        public FeeStructure build() {
            FeeStructure feeStructure = new FeeStructure();
            feeStructure.setFee(fee);
            return feeStructure;
        }
    }
}
