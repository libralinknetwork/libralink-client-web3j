package io.libralink.client.payment.protocol.api.echeck;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.echeck.DepositApproval;
import io.libralink.client.payment.protocol.exception.BuilderException;

import java.util.List;

public class DepositRequest extends APIObject {

    private AbstractEntity check;
    private List<AbstractEntity> depositApprovals;

    public AbstractEntity getCheck() {
        return check;
    }

    void setCheck(AbstractEntity check) {
        this.check = check;
    }

    public List<AbstractEntity> getDepositApprovals() {
        return depositApprovals;
    }

    void setDepositApprovals(List<AbstractEntity> depositApprovals) {
        this.depositApprovals = depositApprovals;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private AbstractEntity check;
        private List<AbstractEntity> depositApprovals;

        private Builder() {}

        public Builder addCheck(AbstractEntity check) {
            this.check = check;
            return this;
        }

        public Builder addDepositApprovals(List<AbstractEntity> depositApprovals) {
            this.depositApprovals = depositApprovals;
            return this;
        }

        public DepositRequest build() throws BuilderException {

            if (check == null || depositApprovals == null || depositApprovals.isEmpty()) {
                throw new BuilderException();
            }

            DepositRequest request = super.build(new DepositRequest());
            request.setCheck(check);
            request.setDepositApprovals(depositApprovals);
            return request;
        }
    }
}
