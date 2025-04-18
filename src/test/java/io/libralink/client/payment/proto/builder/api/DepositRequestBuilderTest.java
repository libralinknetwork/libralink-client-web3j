package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

public class DepositRequestBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_attributes() throws BuilderException {
        DepositRequestBuilder.newBuilder()
            .build();
    }
}
