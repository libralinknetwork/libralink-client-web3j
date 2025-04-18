package io.libralink.client.payment.proto.builder.envelope;

import com.google.protobuf.Any;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class EnvelopeContentBuilder {

    private Any entity;
    private String address;
    private String pubKey;
    private Libralink.SignatureReason sigReason;
    private String algorithm;

    private EnvelopeContentBuilder() {
    }

    private EnvelopeContentBuilder(Libralink.EnvelopeContent content) {
        this.entity = content.getEntity();
        this.address = content.getAddress();
        this.pubKey = content.getPubKey();
        this.sigReason = content.getReason();
        this.algorithm = content.getAlgorithm();
    }

    public static EnvelopeContentBuilder newBuilder() {
        return new EnvelopeContentBuilder();
    }

    public static EnvelopeContentBuilder newBuilder(Libralink.EnvelopeContent content) {
        return new EnvelopeContentBuilder(content);
    }

    public EnvelopeContentBuilder addEntity(Any entity) {
        this.entity = entity;
        return this;
    }

    public EnvelopeContentBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public EnvelopeContentBuilder addPubKey(String pubKey) {
        this.pubKey = pubKey;
        return this;
    }

    public EnvelopeContentBuilder addAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public EnvelopeContentBuilder addSigReason(Libralink.SignatureReason sigReason) {
        this.sigReason = sigReason;
        return this;
    }

    public Libralink.EnvelopeContent build() throws BuilderException {

        if (entity == null) {
            throw new BuilderException();
        }

        if ("DILITHIUM5".equals(algorithm) && pubKey == null) {
            throw new BuilderException();
        }

        Libralink.EnvelopeContent.Builder builder = Libralink.EnvelopeContent.newBuilder()
                .setEntity(entity);

        if (address != null) builder.setAddress(address);
        if (algorithm != null) builder.setAlgorithm(algorithm);
        if (pubKey != null) builder.setPubKey(pubKey);
        if (sigReason != null) builder.setReason(sigReason);

        return builder.build();
    }
}
