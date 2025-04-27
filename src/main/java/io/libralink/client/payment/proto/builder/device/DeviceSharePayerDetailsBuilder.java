package io.libralink.client.payment.proto.builder.device;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class DeviceSharePayerDetailsBuilder {

    private String challenge;
    private String from;
    private String fromProc;

    private DeviceSharePayerDetailsBuilder() {}

    public static DeviceSharePayerDetailsBuilder newBuilder() {
        return new DeviceSharePayerDetailsBuilder();
    }

    public DeviceSharePayerDetailsBuilder addFrom(String from) {
        this.from = from;
        return this;
    }

    public DeviceSharePayerDetailsBuilder addFromProc(String fromProc) {
        this.fromProc = fromProc;
        return this;
    }

    public DeviceSharePayerDetailsBuilder addChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    public Libralink.DeviceSharePayerDetails build() throws BuilderException {

        if (from == null || fromProc == null) {
            throw new BuilderException();
        }

        Libralink.DeviceSharePayerDetails.Builder builder = Libralink.DeviceSharePayerDetails.newBuilder()
            .setFrom(from)
            .setFromProc(fromProc);

        if (challenge != null) {
            builder.setChallenge(challenge);
        }

        return builder.build();
    }
}
