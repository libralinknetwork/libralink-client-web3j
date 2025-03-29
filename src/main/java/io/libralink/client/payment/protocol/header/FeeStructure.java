package io.libralink.client.payment.protocol.header;

import java.math.BigDecimal;

public class FeeStructure {

    private BigDecimal flatFee;
    private BigDecimal percentFee;

    public BigDecimal getFlatFee() {
        return flatFee;
    }

    private FeeStructure() {}

    void setFlatFee(BigDecimal flatFee) {
        this.flatFee = flatFee;
    }

    public BigDecimal getPercentFee() {
        return percentFee;
    }

    void setPercentFee(BigDecimal percentFee) {
        this.percentFee = percentFee;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private BigDecimal flatFee;
        private BigDecimal percentFee;

        private Builder() {}

        public Builder addFlatFee(BigDecimal flatFee) {
            this.flatFee = flatFee;
            return this;
        }

        public Builder addPercentFee(BigDecimal percentFee) {
            this.percentFee = percentFee;
            return this;
        }

        public FeeStructure build() {
            FeeStructure feeStructure = new FeeStructure();
            feeStructure.setPercentFee(percentFee);
            feeStructure.setFlatFee(flatFee);
            return feeStructure;
        }
    }
}
