package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

public class GetBalanceRequestBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_pub() throws BuilderException {
        GetBalanceRequestBuilder.newBuilder()
                .build();
    }

    @Test
    public void test_no_issues() throws BuilderException {
        GetBalanceRequestBuilder.newBuilder()
                .addAddress("0x12345")
                .build();
    }
}
