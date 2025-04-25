package io.libralink.client.payment.signature;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.echeck.PaymentRequestBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.proto.builder.fee.ProcessingFeeBuilder;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

public class SignatureHelperTest {

    final String PARTY_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    final Credentials partyCred = Credentials.create(PARTY_PK);

    final Libralink.PaymentRequest approval = PaymentRequestBuilder.newBuilder()
            .addCreatedAt(1743526954033L)
            .addCorrelationId(UUID.fromString("4f775a54-f028-4632-a3ba-10f85905b148"))
            .addFrom("0x12345")
            .addFromProc("0x12345")
            .addTo("0x12346")
            .addToProc("0x12346")
            .addCurrency("USDC")
            .addAmount(BigDecimal.valueOf(100))
            .build();

    Libralink.EnvelopeContent approvalEnvelopeContent = EnvelopeContentBuilder.newBuilder()
            .addPaymentRequest(approval)
            .build();

    Libralink.Envelope signedApprovalEnvelope = SignatureHelper.sign(
        EnvelopeBuilder.newBuilder()
            .addContent(approvalEnvelopeContent)
            .addId(UUID.randomUUID())
            .build(),
        partyCred,
        Libralink.SignatureReason.CONFIRM
    );

    final Libralink.ProcessingFee processingFee = ProcessingFeeBuilder.newBuilder()
            .addFeeType("flat")
            .addAmount(BigDecimal.valueOf(2.5))
            .addIntermediary("intermediary_1")
            .addEnvelope(signedApprovalEnvelope)
            .build();

    public SignatureHelperTest() throws Exception {
    }

    @Test
    public void test_party_header_content_valid_signature() throws Exception {

        Libralink.EnvelopeContent envelopeContent = EnvelopeContentBuilder.newBuilder()
                .addProcessingFee(processingFee)
                .addAddress(partyCred.getAddress())
                .addSigReason(Libralink.SignatureReason.CONFIRM)
                .build();

        Libralink.Envelope envelope = EnvelopeBuilder.newBuilder()
                .addContent(envelopeContent)
                .addId(UUID.randomUUID())
                .build();

        final Libralink.Envelope signedEnvelope = SignatureHelper.sign(envelope, partyCred, Libralink.SignatureReason.CONFIRM);
        assertNotNull(signedEnvelope.getId());
        assertNotNull(signedEnvelope.getSig());

        boolean isValid = SignatureHelper.verify(signedEnvelope);
        assertTrue(isValid);
    }

    @Test
    public void test_party_header_content_invalid_signature() throws Exception {

        Libralink.EnvelopeContent envelopeContent = EnvelopeContentBuilder.newBuilder()
                .addProcessingFee(processingFee)
                .addAddress("0x127cc4d943dff0a4bd6b024a96554a84e6247440")
                .addSigReason(Libralink.SignatureReason.CONFIRM)
                .build();

        Libralink.Envelope envelope = EnvelopeBuilder.newBuilder()
                .addContent(envelopeContent)
                .addSig("0x051267ae319cd23083c116f43e2d41966354a69e61824a2c922edde4a6df407b74e1db37f82eb5da421f7a19e80bf5bb95fbe875e4d9df186ca73f1c8d7ed65b1c")
                .addId(UUID.randomUUID())
                .build();

        boolean isValid = SignatureHelper.verify(envelope);
        assertFalse(isValid);
    }
}
