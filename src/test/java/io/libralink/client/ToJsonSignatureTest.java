package io.libralink.client;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.echeck.ECheck;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.EnvelopeContent;
import io.libralink.client.payment.protocol.envelope.SignatureReason;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;
import io.libralink.client.payment.protocol.processing.ProcessingFee;
import io.libralink.client.payment.signature.SignatureHelper;
import io.libralink.client.payment.util.JsonUtils;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ToJsonSignatureTest {

    /* Payer Credentials */
    final private String PAYER_PK = "7af8df13f6aebcbd9edd369bb5f67bf7523517685491fea776bb547910ff5673";
    final private Credentials PAYER_CRED = Credentials.create(PAYER_PK);

    /* PAYEE */
    final private String PAYEE_PK = "64496cc969654b231087af38ce654aa8d539fea0970d90366e42a5e39761f3f5";
    final private Credentials PAYEE_CRED = Credentials.create(PAYEE_PK);

    /* Processor Credentials */
    private String PROCESSOR_PK = "1c9f40ff758f70b5c59c9df78738fdd122c4f5d6324e61448a3f516b7df00b22";
    final private Credentials PROCESSOR_CRED = Credentials.create(PROCESSOR_PK);

    @Test
    public void to_json_test() throws Exception {

        /* Payer composes E-Check and sends to Processor */

        final ECheck eCheck = ECheck.builder()
            .addPayer(PAYER_CRED.getAddress())
            .addPayerProcessor(PROCESSOR_CRED.getAddress())
            .addPayee(PAYEE_CRED.getAddress())
            .addPayeeProcessor(PROCESSOR_CRED.getAddress())
            .addCurrency("USDC")
            .addCreatedAt(1743526954033L)
            .addExpiresAt(2743526954133L)
            .addNote("Online courses payment, order #123")
            .addFaceAmount(BigDecimal.valueOf(150))
            .addId(UUID.fromString("bfcb823c-4506-4e17-b715-59de993d15fe"))
            .build();

        EnvelopeContent eCheckContent = EnvelopeContent.builder()
                .addEntity(eCheck)
            .build();

        AbstractEntity.AbstractEntityBuilder eCheckEnvelopeBuilder = Envelope.builder()
                .addContent(eCheckContent)
                .addId(UUID.fromString("19360ffc-dd19-4294-99ed-d0858082b48d"));

        Envelope envelope = eCheckEnvelopeBuilder.build();
        Envelope signedEnvelope = SignatureHelper.sign(envelope, PAYER_CRED, SignatureReason.IDENTITY);
        System.out.println(signedEnvelope.getSig());

        String json = JsonUtils.toJson(signedEnvelope);
        System.out.println(json);

//        Envelope deserilaized = JsonUtils.fromJson(json, Envelope.class);
//        assertNotNull(deserilaized);

        /* Processor verifies, adds fee and locks them by the Signature */

        ProcessingDetails details = ProcessingDetails.builder()
                .addEnvelope(signedEnvelope)
                .addIntermediary(null)
                .addFee(ProcessingFee.builder()
                        .addAmount(BigDecimal.valueOf(1.5))
                        .addFeeType("percent")
                        .build())
                .addId(UUID.fromString("9f3b5bde-b5fb-43b5-90b2-e32238d460be"))
                .build();

        EnvelopeContent detailsEnvelopeContent = EnvelopeContent.builder()
                .addEntity(details)
                .build();

        Envelope detailsEnvelope = Envelope.builder()
                .addContent(detailsEnvelopeContent)
                .addId(UUID.fromString("a3bea111-713d-4972-8a50-8a33a8ea9cf5"))
                .build();

        Envelope detailsEnvelopeSigned = SignatureHelper.sign(detailsEnvelope, PROCESSOR_CRED, SignatureReason.FEE_LOCK);

        /* Payer agrees with Fee and adds confirmation signature */

        EnvelopeContent payerConfirmContent = EnvelopeContent.builder()
                .addEntity(detailsEnvelopeSigned)
                .build();

        Envelope payerConfirmEnvelope = Envelope.builder()
                .addContent(payerConfirmContent)
                .addId(UUID.fromString("fe19bd71-b2fa-4ad2-9c3c-d5e01a1908f2"))
                .build();

        Envelope payerConfirmEnvelopeSigned = SignatureHelper.sign(payerConfirmEnvelope, PAYER_CRED, SignatureReason.CONFIRM);

        /* Processor blocks Payer funds and adds confirmation signature */

        EnvelopeContent processorConfirmContent = EnvelopeContent.builder()
                .addEntity(payerConfirmEnvelopeSigned)
                .build();

        Envelope processorConfirmEnvelope = Envelope.builder()
                .addContent(processorConfirmContent)
                .addId(UUID.fromString("44d00856-57ff-482d-b7fc-2762e88dccdb"))
                .build();

        Envelope processorConfirmEnvelopeSigned = SignatureHelper.sign(processorConfirmEnvelope, PROCESSOR_CRED, SignatureReason.CONFIRM);
        System.out.println(JsonUtils.toJson(processorConfirmEnvelopeSigned));

        assertEquals("0x1c9c03a3a41417e8afa06482c8a8c49bf3b3774edf667ea2fa600173a981c3bd5b42a966526dc720b95138d7480f33d877f9682455d2eabe63f067d02c9b811d1b", processorConfirmEnvelopeSigned.getSig());
    }
}
