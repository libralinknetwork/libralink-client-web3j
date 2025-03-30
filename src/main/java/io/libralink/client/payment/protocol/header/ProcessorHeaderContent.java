package io.libralink.client.payment.protocol.header;

public class ProcessorHeaderContent implements HeaderContent {

    private final String objectType = ProcessorHeaderContent.class.getSimpleName();
    private FeeStructure fee;

    private ProcessorHeaderContent() {}

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

        public ProcessorHeaderContent build() {
            ProcessorHeaderContent partyHeaderContent = new ProcessorHeaderContent();
            partyHeaderContent.setFee(fee);
            return partyHeaderContent;
        }
    }
}
