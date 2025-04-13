package io.libralink.client.payment.protocol.api.account;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

public class RegisterKeyRequestBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_pub() throws BuilderException {
        RegisterKeyRequest.builder()
                .addChallenge("challenge")
                .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_challenge() throws BuilderException {
        RegisterKeyRequest.builder()
                .addAddress("0x12345")
                .build();
    }

    @Test
    public void test_no_issues() throws BuilderException {
        RegisterKeyRequest.builder()
            .addAddress("0x12345")
            .addChallenge("challenge")
            .build();
    }
}
