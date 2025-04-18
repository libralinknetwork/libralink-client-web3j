package io.libralink.client.payment.proto.builder.envelope;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

public class EnvelopeBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_content() throws BuilderException {
        EnvelopeBuilder.newBuilder()
            .addSig("sig")
            .build();
    }
}
