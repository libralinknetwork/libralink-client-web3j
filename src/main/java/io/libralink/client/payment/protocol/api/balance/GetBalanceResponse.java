package io.libralink.client.payment.protocol.api.balance;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

import java.math.BigDecimal;

public class GetBalanceResponse extends APIObject {

    private String address;
    private BigDecimal available;
    private BigDecimal pending;

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
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

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private String address;
        private BigDecimal available;
        private BigDecimal pending;

        private Builder() {}

        public Builder addAddress(String address) {
            this.address = address;
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

        public GetBalanceResponse build() throws BuilderException {

            if (address == null || available == null || pending == null) {
                throw new BuilderException();
            }

            GetBalanceResponse response = super.build(new GetBalanceResponse());
            response.setAddress(address);
            response.setAvailable(available);
            response.setPending(pending);
            return response;
        }
    }
}
