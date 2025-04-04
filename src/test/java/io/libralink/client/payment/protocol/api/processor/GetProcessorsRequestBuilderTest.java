package io.libralink.client.payment.protocol.api.processor;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

public class GetProcessorsRequestBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_pub() throws BuilderException {
        GetProcessorsRequest.builder()
                .build();
    }

    @Test
    public void test_no_issues() throws BuilderException {
        GetProcessorsRequest.builder()
                .addPub("0x12345")
                .build();
    }
}
