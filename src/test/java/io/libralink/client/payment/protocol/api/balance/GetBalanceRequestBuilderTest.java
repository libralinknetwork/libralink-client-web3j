package io.libralink.client.payment.protocol.api.balance;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

public class GetBalanceRequestBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_pub() throws BuilderException {
        GetBalanceRequest.builder()
                .build();
    }

    @Test
    public void test_no_issues() throws BuilderException {
        GetBalanceRequest.builder()
                .addPub("0x12345")
                .build();
    }
}
