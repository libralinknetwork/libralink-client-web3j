package io.libralink.client.payment.protocol.processing;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class ProcessingDetails extends AbstractEntity {

    private ProcessingFee fee;
    private String intermediary;
    private Envelope envelope;

    public Envelope getEnvelope() {
        return envelope;
    }

    void setEnvelope(Envelope envelope) {
        this.envelope = envelope;
    }

    public ProcessingFee getFee() {
        return fee;
    }

    void setFee(ProcessingFee fee) {
        this.fee = fee;
    }

    public String getIntermediary() {
        return intermediary;
    }

    void setIntermediary(String intermediary) {
        this.intermediary = intermediary;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends AbstractEntityBuilder {

        private ProcessingFee fee;
        private String intermediary;
        private Envelope envelope;

        private Builder() {}

        public Builder addEnvelope(Envelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder addFee(ProcessingFee fee) {
            this.fee = fee;
            return this;
        }

        public Builder addIntermediary(String intermediary) {
            this.intermediary = intermediary;
            return this;
        }

        public ProcessingDetails build() throws BuilderException {

            if (fee == null || envelope == null) {
                throw new BuilderException();
            }

            ProcessingDetails details = super.build(new ProcessingDetails());
            details.setFee(fee);
            details.setEnvelope(envelope);
            details.setIntermediary(intermediary);
            return details;
        }
    }
}
