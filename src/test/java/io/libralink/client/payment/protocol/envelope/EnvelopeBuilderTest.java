package io.libralink.client.payment.protocol.envelope;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

public class EnvelopeBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_content() throws BuilderException {

        Envelope.builder()
                .addSignature(Signature.builder()
                    .addSig("sig")
                    .addPub("pub")
                    .build()
                ).build();
    }
}
