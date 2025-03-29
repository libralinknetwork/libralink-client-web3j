package io.libralink.client;

import io.libralink.client.payment.protocol.Envelope;
import io.libralink.client.payment.protocol.body.BodyContent;
import io.libralink.client.payment.protocol.body.BodyEnvelope;
import io.libralink.client.payment.protocol.body.DepositReceiptBody;
import io.libralink.client.payment.protocol.error.ErrorEnvelope;
import io.libralink.client.payment.protocol.error.ErrorMessage;
import io.libralink.client.payment.protocol.header.*;
import io.libralink.client.payment.util.JsonUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToJsonSignatureTest {

    @Test
    public void toJsonTest() throws Exception {

        BodyContent bodyContent = DepositReceiptBody.builder()
            .addAmount(BigDecimal.ONE)
            .addCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
            .addPayee("0x1234")
            .addPayer("0x1235")
            .addECheckEnvelopeId(UUID.randomUUID())
            .addPaymentRequestEnvelopeIds(new ArrayList<>())
            .addType("USDT")
            .build();

        BodyEnvelope bodyEnvelope = BodyEnvelope.builder()
            .addContent(bodyContent).build();

        FeeStructure feeStructure = FeeStructure.builder()
            .addFlatFee(BigDecimal.ONE)
            .addPercentFee(BigDecimal.TEN)
            .build();

        PartyHeaderContent headerContent = PartyHeaderContent.builder()
            .addFee(feeStructure).build();

        Signature headerSig = Signature.builder()
            .addAddress("address1")
            .addSig("sig1")
            .addNonce("nonce1")
            .build();

        Signature bodySig = Signature.builder()
            .addAddress("address2")
            .addSig("sig2")
            .addNonce("nonce2")
            .build();

        HeaderWithSignature headerWithSignature = HeaderWithSignature.builder()
            .addHeaderSig(headerSig)
            .addBodySig(bodySig)
            .addContent(headerContent)
            .build();

        HeaderEnvelope headerEnvelope = HeaderEnvelope.builder()
            .addContent(List.of(headerWithSignature)).build();


        ErrorMessage errorMessage = ErrorMessage.builder()
            .addCode(1)
            .addMessage("Test Error")
            .build();

        ErrorEnvelope errorEnvelope = ErrorEnvelope.builder()
            .addContent(errorMessage).build();

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
