package io.libralink.client.payment.protocol.header;

public class PartyHeaderContent implements HeaderContent {

    private final String objectType = PartyHeaderContent.class.getSimpleName();
    private FeeStructure fee;

    private PartyHeaderContent() {}

    public FeeStructure getFee() {
        return fee;
    }

    void setFee(FeeStructure fee) {
        this.fee = fee;
    }

    public String getObjectType() {
        return objectType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private FeeStructure fee;

        private Builder() {}

        public Builder addFee(FeeStructure fee) {
            this.fee = fee;
            return this;
        }

        public PartyHeaderContent build() {
            PartyHeaderContent partyHeaderContent = new PartyHeaderContent();
            partyHeaderContent.setFee(fee);
            return partyHeaderContent;
        }
    }
}
