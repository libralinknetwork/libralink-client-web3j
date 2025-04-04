package io.libralink.client.payment.protocol.envelope;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class Envelope extends AbstractEntity {

    private AbstractEntity content;
    private Signature signature;

    public AbstractEntity getContent() {
        return content;
    }

    void setContent(AbstractEntity content) {
        this.content = content;
    }

    public Signature getSignature() {
        return signature;
    }

    void setSignature(Signature signature) {
        this.signature = signature;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Envelope envelope) {
        return new Builder(envelope);
    }

    public static class Builder extends AbstractEntityBuilder {

        private AbstractEntity content;
        private Signature signature;

        private Builder() {
            super();
        }

        private Builder(Envelope envelope) {
            this.content = envelope.content;
            this.signature = envelope.signature;
        }

        public Builder addContent(AbstractEntity content) {
            this.content = content;
            return this;
        }

        public Builder addSignature(Signature signature) {
            this.signature = signature;
            return this;
        }

        protected <T extends Envelope> T build(T envelope) throws BuilderException {
            super.build(envelope);

            envelope.setContent(content);
            envelope.setSignature(signature);
            return envelope;
        }

        public Envelope build() throws BuilderException {

            if (content == null) {
                throw new BuilderException();
            }

            Envelope envelope = super.build(new Envelope());
            envelope.setContent(content);
            envelope.setSignature(signature);
            return envelope;
        }
    }
}
