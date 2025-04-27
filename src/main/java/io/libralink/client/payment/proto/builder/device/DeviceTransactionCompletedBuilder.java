package io.libralink.client.payment.proto.builder.device;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

public final class DeviceTransactionCompletedBuilder {

    private String correlationId;

    private DeviceTransactionCompletedBuilder() {}

    public static DeviceTransactionCompletedBuilder newBuilder() {
        return new DeviceTransactionCompletedBuilder();
    }

    public DeviceTransactionCompletedBuilder addCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public Libralink.DeviceTransactionCompleted build() throws BuilderException {

        if (correlationId == null) {
            throw new BuilderException();
        }

        return Libralink.DeviceTransactionCompleted.newBuilder()
            .setCorrelationId(correlationId)
                .build();
    }
}
