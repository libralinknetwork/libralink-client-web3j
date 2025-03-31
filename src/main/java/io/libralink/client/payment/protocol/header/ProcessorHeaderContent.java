package io.libralink.client.payment.protocol.header;

public class ProcessorHeaderContent implements HeaderContent {

    private final String objectType = ProcessorHeaderContent.class.getSimpleName();
    private String intermediary;
    private FeeStructure fee;

    private ProcessorHeaderContent() {}

    public FeeStructure getFee() {
        return fee;
    }

    void setFee(FeeStructure fee) {
        this.fee = fee;
    }

    public String getIntermediary() {
        return intermediary;
    }

    void setIntermediary(String intermediary) {
        this.intermediary = intermediary;
    }

    public String getObjectType() {
        return objectType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private FeeStructure fee;
        private String intermediary;

        private Builder() {}

        public Builder addFee(FeeStructure fee) {
            this.fee = fee;
            return this;
        }

        public Builder addIntermediary(String intermediary) {
            this.intermediary = intermediary;
            return this;
        }

        public ProcessorHeaderContent build() {
            ProcessorHeaderContent partyHeaderContent = new ProcessorHeaderContent();
            partyHeaderContent.setFee(fee);
            partyHeaderContent.setIntermediary(intermediary);
            return partyHeaderContent;
        }
    }
}
