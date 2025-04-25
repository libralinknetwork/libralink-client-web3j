package io.libralink.client.payment.proto.builder.api;

import com.google.protobuf.InvalidProtocolBufferException;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ErrorResponseBuilderTest {

    @Test
    public void test_build_error_envelope() throws BuilderException, InvalidProtocolBufferException {

        Libralink.ErrorResponse response = ErrorResponseBuilder.newBuilder()
                .addCode(1)
                .addMessage("Error 1")
                .build();

        Libralink.EnvelopeContent envelopeContent = EnvelopeContentBuilder.newBuilder()
                .addErrorResponse(response)
                .addAddress("fake")
                .addSigReason(Libralink.SignatureReason.NONE)
                .build();

        Libralink.Envelope envelope = EnvelopeBuilder.newBuilder()
                .addContent(envelopeContent)
                .addSig(null)
                .addId(UUID.fromString("84606adc-e558-40d4-9b6c-c7c005dae3fa"))
                .build();

        Libralink.ErrorResponse unpacked = envelope.getContent().getErrorResponse();
        assertEquals(1, unpacked.getCode());
        assertEquals("Error 1", unpacked.getMessage());
        assertEquals("84606adc-e558-40d4-9b6c-c7c005dae3fa", envelope.getId());
    }
}
