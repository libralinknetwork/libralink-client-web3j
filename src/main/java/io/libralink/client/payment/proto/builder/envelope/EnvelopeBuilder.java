package io.libralink.client.payment.proto.builder.envelope;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.util.UUID;

public final class EnvelopeBuilder {

    private Libralink.EnvelopeContent content;
    private String sig;
    private UUID id;

    private EnvelopeBuilder() {}

    private EnvelopeBuilder(Libralink.Envelope envelope) {
        this.content = envelope.getContent();
        this.sig = envelope.getSig();
        this.id = UUID.fromString(envelope.getId());
    }

    public static EnvelopeBuilder newBuilder() {
        return new EnvelopeBuilder();
    }

    public static EnvelopeBuilder newBuilder(Libralink.Envelope envelope) {
        return new EnvelopeBuilder(envelope);
    }

    public EnvelopeBuilder addId(UUID id) {
        this.id = id;
        return this;
    }

    public EnvelopeBuilder addContent(Libralink.EnvelopeContent content) {
        this.content = content;
        return this;
    }

    public EnvelopeBuilder addSig(String sig) {
        this.sig = sig;
        return this;
    }

    public Libralink.Envelope build() throws BuilderException {

        if (content == null || id == null) {
            throw new BuilderException();
        }

        Libralink.Envelope.Builder builder = Libralink.Envelope.newBuilder()
                .setId(id.toString())
                .setContent(content);

        if (sig != null) builder.setSig(sig);

        return builder.build();
    }
}
