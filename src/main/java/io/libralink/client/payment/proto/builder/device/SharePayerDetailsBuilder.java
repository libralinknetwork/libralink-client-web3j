package io.libralink.client.payment.proto.builder.device;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class SharePayerDetailsBuilder {

    private String challenge;
    private String from;
    private String fromProc;

    private SharePayerDetailsBuilder() {}

    public static SharePayerDetailsBuilder newBuilder() {
        return new SharePayerDetailsBuilder();
    }

    public SharePayerDetailsBuilder addFrom(String from) {
        this.from = from;
        return this;
    }

    public SharePayerDetailsBuilder addFromProc(String fromProc) {
        this.fromProc = fromProc;
        return this;
    }

    public SharePayerDetailsBuilder addChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    public Libralink.SharePayerDetails build() throws BuilderException {

        if (from == null || fromProc == null) {
            throw new BuilderException();
        }

        Libralink.SharePayerDetails.Builder builder = Libralink.SharePayerDetails.newBuilder()
            .setFrom(from)
            .setFromProc(fromProc);

        if (challenge != null) {
            builder.setChallenge(challenge);
        }

        return builder.build();
    }
}
