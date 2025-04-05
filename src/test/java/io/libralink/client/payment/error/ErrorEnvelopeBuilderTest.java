package io.libralink.client.payment.error;

import io.libralink.client.payment.protocol.api.error.ErrorResponse;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.EnvelopeContent;
import io.libralink.client.payment.protocol.envelope.SignatureReason;
import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ErrorEnvelopeBuilderTest {

    @Test
    public void test_build_error_envelope() throws BuilderException {

        ErrorResponse response = ErrorResponse.builder()
                .addCode(1)
                .addMessage("Error 1")
                .addId(UUID.fromString("ee6c2125-6832-4aa4-ae03-425439cb89f3"))
                .build();

        EnvelopeContent envelopeContent = EnvelopeContent.builder()
                .addEntity(response)
                .addPub("fake")
                .addSigReason(SignatureReason.NONE)
                .build();

        Envelope envelope = Envelope.builder()
                .addContent(envelopeContent)
                .addSig(null)
                .addId(UUID.fromString("84606adc-e558-40d4-9b6c-c7c005dae3fa"))
                .build();

        assertEquals(1, ((ErrorResponse) envelope.getContent().getEntity()).getCode());
        assertEquals("Error 1", ((ErrorResponse) envelope.getContent().getEntity()).getMessage());
        assertEquals("ee6c2125-6832-4aa4-ae03-425439cb89f3", envelope.getContent().getEntity().getId().toString());
    }
}
