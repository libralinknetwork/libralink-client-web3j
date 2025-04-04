package io.libralink.client.payment.protocol.envelope;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

public class SignatureBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_sig() throws BuilderException {

        Signature.builder()
            .addPub("pub_key")
                .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_pub() throws BuilderException {

        Signature.builder()
                .addSig("signature")
                .build();
    }
}
