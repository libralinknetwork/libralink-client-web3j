package io.libralink.client.payment.protocol.api.echeck;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

public class DepositRequestBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_attributes() throws BuilderException {
        DepositRequest.builder()
            .build();
    }
}
