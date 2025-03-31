package io.libralink.client;

import io.libralink.client.payment.protocol.Envelope;
import io.libralink.client.payment.protocol.body.BodyContent;
import io.libralink.client.payment.protocol.body.BodyEnvelope;
import io.libralink.client.payment.protocol.body.PaymentRequestBody;
import io.libralink.client.payment.protocol.error.ErrorEnvelope;
import io.libralink.client.payment.protocol.error.ErrorMessage;
import io.libralink.client.payment.protocol.header.*;
import io.libralink.client.payment.util.JsonUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToJsonSignatureTest {

    @Test
    public void toJsonTest() throws Exception {

        BodyContent bodyContent = PaymentRequestBody.builder()
            .addAmount(BigDecimal.ONE)
            .addCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
            .addPayee("0x1234")
            .addPayer("0x1235")
            .addType("USDT")
            .build();

        BodyEnvelope bodyEnvelope = BodyEnvelope.builder()
            .addBody(bodyContent).build();

        FeeStructure feeStructure = FeeStructure.builder()
            .addFee(BigDecimal.ONE)
            .build();

        ProcessorHeaderContent headerContent = ProcessorHeaderContent.builder()
            .addFee(feeStructure).addIntermediary(UUID.randomUUID().toString()).build();

        Signature headerSig = Signature.builder()
            .addAddress("address1")
            .addSig("sig1")
            .addSalt("nonce1")
            .build();

        Signature bodySig = Signature.builder()
            .addAddress("address2")
            .addSig("sig2")
            .addSalt("nonce2")
            .build();

        HeaderWithSignature headerWithSignature = HeaderWithSignature.builder()
            .addHeaderSig(headerSig)
            .addBodySig(bodySig)
            .addHeader(headerContent)
            .build();

        HeaderEnvelope headerEnvelope = HeaderEnvelope.builder()
            .addHeaders(List.of(headerWithSignature)).build();


        ErrorMessage errorMessage = ErrorMessage.builder()
            .addCode(1)
            .addMessage("Test Error")
            .build();

        ErrorEnvelope errorEnvelope = ErrorEnvelope.builder()
            .addError(errorMessage).build();

        Envelope envelope = Envelope.builder()
            .addBody(bodyEnvelope)
            .addHeader(headerEnvelope)
            .addEnvelopeId(UUID.randomUUID())
            .addError(errorEnvelope)
            .build();

        String json = JsonUtils.toJson(envelope);
        System.out.println(json);

        Envelope deserilaized = JsonUtils.fromJson(json, Envelope.class);
        assertNotNull(deserilaized);
    }
}
