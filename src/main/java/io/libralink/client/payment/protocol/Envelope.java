package io.libralink.client.payment.protocol;

import io.libralink.client.payment.protocol.body.BodyEnvelope;
import io.libralink.client.payment.protocol.error.ErrorEnvelope;
import io.libralink.client.payment.protocol.header.HeaderEnvelope;

import java.util.UUID;

public class Envelope {

    private int protocolVersion = 1;
    private UUID envelopeId;
    private HeaderEnvelope header;
    private BodyEnvelope body;
    private ErrorEnvelope error;

    private Envelope() {}

    public int getProtocolVersion() {
        return protocolVersion;
    }

    void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public UUID getEnvelopeId() {
        return envelopeId;
    }

    void setEnvelopeId(UUID envelopeId) {
        this.envelopeId = envelopeId;
    }

    public HeaderEnvelope getHeader() {
        return header;
    }

    void setHeader(HeaderEnvelope header) {
        this.header = header;
    }

    public BodyEnvelope getBody() {
        return body;
    }

    void setBody(BodyEnvelope body) {
        this.body = body;
    }

    public ErrorEnvelope getError() {
        return error;
    }

    void setError(ErrorEnvelope error) {
        this.error = error;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Envelope envelope) {
        return new Builder(envelope.getEnvelopeId(), envelope.getHeader(), envelope.getBody(), envelope.getError());
    }

    public final static class Builder {

        private UUID envelopeId;
        private HeaderEnvelope header;
        private BodyEnvelope body;
        private ErrorEnvelope error;

        private Builder() {}

        public Builder(UUID envelopeId, HeaderEnvelope header, BodyEnvelope body, ErrorEnvelope error) {
            this.envelopeId = envelopeId;
            this.header = header;
            this.body = body;
            this.error = error;
        }

        public Builder addError(ErrorEnvelope error) {
            this.error = error;
            return this;
        }

        public Builder addBody(BodyEnvelope body) {
            this.body = body;
            return this;
        }

        public Builder addHeader(HeaderEnvelope header) {
            this.header = header;
            return this;
        }

        public Builder addEnvelopeId(UUID envelopeId) {
            this.envelopeId = envelopeId;
            return this;
        }

        public Envelope build() {
            Envelope envelope = new Envelope();
            envelope.setError(error);
            envelope.setEnvelopeId(envelopeId);
            envelope.setHeader(header);
            envelope.setBody(body);
            return envelope;
        }
    }
}
