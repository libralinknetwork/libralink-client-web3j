package io.libralink.client.payment.proto.builder.device;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.util.List;

public final class DeviceSharePayerDetailsBuilder {

    private String challenge;
    private String from;
    private List<String> processors;

    private DeviceSharePayerDetailsBuilder() {}

    public static DeviceSharePayerDetailsBuilder newBuilder() {
        return new DeviceSharePayerDetailsBuilder();
    }

    public DeviceSharePayerDetailsBuilder addFrom(String from) {
        this.from = from;
        return this;
    }

    public DeviceSharePayerDetailsBuilder addProcessors(List<String> processors) {
        this.processors = processors;
        return this;
    }

    public DeviceSharePayerDetailsBuilder addChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    public Libralink.DeviceSharePayerDetails build() throws BuilderException {

        if (from == null || processors == null || processors.isEmpty()) {
            throw new BuilderException();
        }

        Libralink.DeviceSharePayerDetails.Builder builder = Libralink.DeviceSharePayerDetails.newBuilder()
            .setFrom(from);

        for (int i=0; i< processors.size(); i++) {
            builder.addProcessors(processors.get(i));
        }

        if (challenge != null) {
            builder.setChallenge(challenge);
        }

        return builder.build();
    }
}
