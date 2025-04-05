package io.libralink.client.payment.protocol.envelope;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.exception.BuilderException;

public class Envelope extends AbstractEntity {

    private EnvelopeContent content;
    private String sig;

    public EnvelopeContent getContent() {
        return content;
    }

    void setContent(EnvelopeContent content) {
        this.content = content;
    }

    public String getSig() {
        return sig;
    }

    void setSig(String sig) {
        this.sig = sig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Envelope envelope) {
        return new Builder(envelope);
    }

    public static class Builder extends AbstractEntityBuilder {

        private EnvelopeContent content;
        private String sig;

        private Builder() {
            super();
        }

        private Builder(Envelope envelope) {
            this.content = envelope.content;
            this.sig = envelope.sig;
            this.addId(envelope.getId());
        }

        public Builder addContent(EnvelopeContent content) {
            this.content = content;
            return this;
        }

        public Builder addSig(String sig) {
            this.sig = sig;
            return this;
        }

        protected <T extends Envelope> T build(T envelope) throws BuilderException {
            super.build(envelope);

            envelope.setContent(content);
            envelope.setSig(sig);
            return envelope;
        }

        public Envelope build() throws BuilderException {

            if (content == null) {
                throw new BuilderException();
            }

            Envelope envelope = super.build(new Envelope());
            envelope.setContent(content);
            envelope.setSig(sig);
            return envelope;
        }
    }
}
