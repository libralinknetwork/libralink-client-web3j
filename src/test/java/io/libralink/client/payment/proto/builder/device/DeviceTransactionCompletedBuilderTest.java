package io.libralink.client.payment.proto.builder.device;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

import java.util.UUID;

public class DeviceTransactionCompletedBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_correlation_id() throws BuilderException {
        DeviceTransactionCompletedBuilder.newBuilder()
            .build();
    }

    @Test
    public void test_no_issues() throws BuilderException {
        DeviceTransactionCompletedBuilder.newBuilder()
                .addCorrelationId(UUID.randomUUID().toString())
                .build();
    }
}
