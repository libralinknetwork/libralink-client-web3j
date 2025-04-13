package io.libralink.client.payment.signature;

import io.libralink.client.payment.protocol.echeck.DepositApproval;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.EnvelopeContent;
import io.libralink.client.payment.protocol.envelope.SignatureReason;
import io.libralink.client.payment.protocol.processing.ProcessingFee;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

public class SignatureHelperTest {

    final String PARTY_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    final Credentials partyCred = Credentials.create(PARTY_PK);

    final DepositApproval approval = DepositApproval.builder()
            .addCreatedAt(1743526954033L)
            .addCheckId(UUID.fromString("4f775a54-f028-4632-a3ba-10f85905b148"))
            .addPayer("0x12345")
            .addPayee("0x12346")
            .addAmount(BigDecimal.valueOf(100))
            .addId(UUID.fromString("8a29d3c8-4b8e-43dc-8542-9abd6231de7a"))
            .build();

    EnvelopeContent approvalEnvelopeContent = EnvelopeContent.builder()
            .addEntity(approval)
            .build();

    Envelope signedApprovalEnvelope = SignatureHelper.sign(
        Envelope.builder()
            .addContent(approvalEnvelopeContent)
            .addId(UUID.randomUUID())
            .build(),
        partyCred,
        SignatureReason.CONFIRM
    );

    final ProcessingFee fee = ProcessingFee.builder()
            .addFeeType("flat")
            .addAmount(BigDecimal.valueOf(2.5))
            .build();
    final ProcessingDetails content = ProcessingDetails.builder()
            .addFee(fee)
            .addIntermediary("intermediary_1")
            .addEnvelope(signedApprovalEnvelope)
            .addId(UUID.fromString("a01f2543-f727-4c41-9684-ed27bcfc7790"))
            .build();

    public SignatureHelperTest() throws Exception {
    }

    @Test
    public void test_party_header_content_valid_signature() throws Exception {

        EnvelopeContent envelopeContent = EnvelopeContent.builder()
                .addEntity(content)
                .addAddress(partyCred.getAddress())
                .addSigReason(SignatureReason.CONFIRM)
                .build();

        Envelope envelope = Envelope.builder()
                .addContent(envelopeContent)
                .addId(UUID.randomUUID())
                .build();

        final Envelope signedEnvelope = SignatureHelper.sign(envelope, partyCred, SignatureReason.CONFIRM);
        assertNotNull(signedEnvelope.getId());
        assertNotNull(signedEnvelope.getSig());

        boolean isValid = SignatureHelper.verify(signedEnvelope);
        assertTrue(isValid);
    }

    @Test
    public void test_party_header_content_invalid_signature() throws Exception {

        EnvelopeContent envelopeContent = EnvelopeContent.builder()
                .addEntity(content)
                .addAddress("0x127cc4d943dff0a4bd6b024a96554a84e6247440")
                .addSigReason(SignatureReason.CONFIRM)
                .build();

        Envelope envelope = Envelope.builder()
                .addContent(envelopeContent)
                .addSig("0x051267ae319cd23083c116f43e2d41966354a69e61824a2c922edde4a6df407b74e1db37f82eb5da421f7a19e80bf5bb95fbe875e4d9df186ca73f1c8d7ed65b1c")
                .addId(UUID.randomUUID())
                .build();

        boolean isValid = SignatureHelper.verify(envelope);
        assertFalse(isValid);
    }
}
