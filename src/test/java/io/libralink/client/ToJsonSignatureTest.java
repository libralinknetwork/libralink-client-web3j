package io.libralink.client;

import com.google.protobuf.Any;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.echeck.ECheckBuilder;
import io.libralink.client.payment.proto.builder.echeck.ECheckSplitBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.proto.builder.fee.ProcessingFeeBuilder;
import io.libralink.client.payment.signature.SignatureHelper;
import io.libralink.client.payment.util.JsonUtils;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.util.List;
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

        final Libralink.ECheck eCheck = ECheckBuilder.newBuilder()
            .addCorrelationId(UUID.fromString("9d451114-ed78-4c25-8893-81b3f83c16e8"))
            .addFrom(PAYER_CRED.getAddress())
            .addFromProc(PROCESSOR_CRED.getAddress())
            .addTo(PAYEE_CRED.getAddress())
            .addToProc(PROCESSOR_CRED.getAddress())
            .addCurrency("USDC")
            .addCreatedAt(1743526954033L)
            .addExpiresAt(2743526954133L)
            .addNote("Online courses payment, order #123")
            .addFaceAmount(BigDecimal.valueOf(150))
            .addSplits(List.of(ECheckSplitBuilder.newBuilder()
                .addTo(PAYEE_CRED.getAddress())
                .addToProc(PROCESSOR_CRED.getAddress())
                .addAmount(BigDecimal.valueOf(150))
                .build()))
            .build();

        Libralink.EnvelopeContent eCheckContent = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(eCheck))
            .build();

        EnvelopeBuilder eCheckEnvelopeBuilder = EnvelopeBuilder.newBuilder()
                .addContent(eCheckContent)
                .addId(UUID.fromString("19360ffc-dd19-4294-99ed-d0858082b48d"));

        Libralink.Envelope envelope = eCheckEnvelopeBuilder.build();
        Libralink.Envelope signedEnvelope = SignatureHelper.sign(envelope, PAYER_CRED, Libralink.SignatureReason.IDENTITY);
        System.out.println(signedEnvelope.getSig());

        String json = JsonUtils.toJson(signedEnvelope);
        System.out.println(json);

//        Envelope deserilaized = JsonUtils.fromJson(json, Envelope.class);
//        assertNotNull(deserilaized);

        /* Processor verifies, adds fee and locks them by the Signature */

        Libralink.ProcessingFee details = ProcessingFeeBuilder.newBuilder()
                .addEnvelope(signedEnvelope)
                .addIntermediary(null)
                .addAmount(BigDecimal.valueOf(1.5))
                .addFeeType("percent")
                .build();

        Libralink.EnvelopeContent detailsEnvelopeContent = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(details))
                .build();

        Libralink.Envelope detailsEnvelope = EnvelopeBuilder.newBuilder()
                .addContent(detailsEnvelopeContent)
                .addId(UUID.fromString("a3bea111-713d-4972-8a50-8a33a8ea9cf5"))
                .build();

        Libralink.Envelope detailsEnvelopeSigned = SignatureHelper.sign(detailsEnvelope, PROCESSOR_CRED, Libralink.SignatureReason.FEE_LOCK);

        /* Payer agrees with Fee and adds confirmation signature */

        Libralink.EnvelopeContent payerConfirmContent = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(detailsEnvelopeSigned))
                .build();

        Libralink.Envelope payerConfirmEnvelope = EnvelopeBuilder.newBuilder()
                .addContent(payerConfirmContent)
                .addId(UUID.fromString("fe19bd71-b2fa-4ad2-9c3c-d5e01a1908f2"))
                .build();

        Libralink.Envelope payerConfirmEnvelopeSigned = SignatureHelper.sign(payerConfirmEnvelope, PAYER_CRED, Libralink.SignatureReason.CONFIRM);

        /* Processor blocks Payer funds and adds confirmation signature */

        Libralink.EnvelopeContent processorConfirmContent = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(payerConfirmEnvelopeSigned))
                .build();

        Libralink.Envelope processorConfirmEnvelope = EnvelopeBuilder.newBuilder()
                .addContent(processorConfirmContent)
                .addId(UUID.fromString("44d00856-57ff-482d-b7fc-2762e88dccdb"))
                .build();

        Libralink.Envelope processorConfirmEnvelopeSigned = SignatureHelper.sign(processorConfirmEnvelope, PROCESSOR_CRED, Libralink.SignatureReason.CONFIRM);
        System.out.println(JsonUtils.toJson(processorConfirmEnvelopeSigned));

        assertEquals("0x906de8273c3da4af8518b6b44ff116bc27167faa05e1e7294b70083985eccbae3cc7dce198fe95eb287a40e7e31f7e75c3acf39e039201351015bebf7197d9ec1b", processorConfirmEnvelopeSigned.getSig());
    }
}
