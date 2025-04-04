package io.libralink.client.payment.protocol.api.echeck;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class DepositResponse extends APIObject {

    private UUID checkId;
    private BigDecimal amount;
    private List<UUID> depositApprovalIds;

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

    public List<UUID> getDepositApprovalIds() {
        return depositApprovalIds;
    }

    void setDepositApprovalIds(List<UUID> depositApprovalIds) {
        this.depositApprovalIds = depositApprovalIds;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private UUID checkId;
        private BigDecimal amount;
        private List<UUID> depositApprovalIds;

        private Builder() {}

        public Builder addCheckId(UUID checkId) {
            this.checkId = checkId;
            return this;
        }

        public Builder addAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder addDepositApprovalIds(List<UUID> depositApprovalIds) {
            this.depositApprovalIds = depositApprovalIds;
            return this;
        }

        public DepositResponse build() throws BuilderException {

            if (amount == null || checkId == null || depositApprovalIds == null || depositApprovalIds.isEmpty()) {
                throw new BuilderException();
            }

            DepositResponse response = super.build(new DepositResponse());
            response.setAmount(amount);
            response.setCheckId(checkId);
            response.setDepositApprovalIds(depositApprovalIds);
            return response;
        }
    }
}
