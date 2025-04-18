package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

import java.util.UUID;

public class RegisterKeyRequestBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_pub() throws BuilderException {
        RegisterKeyRequestBuilder.newBuilder()
                .addHash("hash")
                .addConfirmationId(UUID.randomUUID().toString())
                .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_challenge() throws BuilderException {
        RegisterKeyRequestBuilder.newBuilder()
                .addAddress("0x12345")
                .build();
    }

    @Test
    public void test_no_issues() throws BuilderException {
        RegisterKeyRequestBuilder.newBuilder()
            .addAddress("0x12345")
            .addHash("hash")
            .addAlgorithm("secp256k1")
            .addConfirmationId(UUID.randomUUID().toString())
            .build();
    }
}
