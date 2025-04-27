package io.libralink.client.payment.proto.builder.device;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

public class DeviceSharePayerDetailsBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_from() throws BuilderException {
        DeviceSharePayerDetailsBuilder.newBuilder()
            .addFromProc("fake")
            .build();
    }

    @Test
    public void test_no_issues() throws BuilderException {
        DeviceSharePayerDetailsBuilder.newBuilder()
                .addFrom("fake")
                .addFromProc("fake")
                .build();
    }
}
