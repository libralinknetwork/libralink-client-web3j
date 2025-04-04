package io.libralink.client.payment.protocol.api.balance;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

import java.math.BigDecimal;

public class GetBalanceResponse extends APIObject {

    private String pub;
    private BigDecimal available;
    private BigDecimal pending;
    private BigDecimal total;

    public String getPub() {
        return pub;
    }

    void setPub(String pub) {
        this.pub = pub;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getPending() {
        return pending;
    }

    void setPending(BigDecimal pending) {
        this.pending = pending;
    }

    public BigDecimal getTotal() {
        return total;
    }

    void setTotal(BigDecimal total) {
        this.total = total;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private String pub;
        private BigDecimal available;
        private BigDecimal pending;
        private BigDecimal total;

        private Builder() {}

        public Builder addPub(String pub) {
            this.pub = pub;
            return this;
        }

        public Builder addAvailable(BigDecimal available) {
            this.available = available;
            return this;
        }

        public Builder addPending(BigDecimal pending) {
            this.pending = pending;
            return this;
        }

        public Builder addTotal(BigDecimal total) {
            this.total = total;
            return this;
        }

        public GetBalanceResponse build() throws BuilderException {

            if (pub == null || available == null || pending == null || total == null) {
                throw new BuilderException();
            }

            GetBalanceResponse response = super.build(new GetBalanceResponse());
            response.setPub(pub);
            response.setAvailable(available);
            response.setPending(pending);
            response.setTotal(total);
            return response;
        }
    }
}
