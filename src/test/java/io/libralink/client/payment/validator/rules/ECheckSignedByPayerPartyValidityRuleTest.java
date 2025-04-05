package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.echeck.ECheck;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.EnvelopeContent;
import io.libralink.client.payment.protocol.envelope.SignatureReason;
import io.libralink.client.payment.signature.SignatureHelper;
import static io.libralink.client.payment.validator.BaseEntityValidator.findFirstFailedRule;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ECheckSignedByPayerPartyValidityRuleTest {

    final String PAYER_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    final Credentials payerCred = Credentials.create(PAYER_PK);

    final ECheck.Builder eCheckBuilder = ECheck.builder()
            .addPayer(payerCred.getAddress())
            .addPayerProcessor("fake")
            .addPayee("fake")
            .addPayeeProcessor("fake")
            .addCurrency("USDC")
            .addCreatedAt(1743526954033L)
            .addExpiresAt(2743526954133L)
            .addFaceAmount(BigDecimal.valueOf(150));

    @Test
    public void test_single_envelope_valid() throws Exception {

        ECheck eCheck = eCheckBuilder.build();

        EnvelopeContent content = EnvelopeContent.builder()
                .addEntity(eCheck)
                .build();

        Envelope envelope = Envelope.builder()
                .addContent(content)
                .build();

        Envelope signedEnvelope = SignatureHelper.sign(envelope, payerCred, SignatureReason.CONFIRM);
        assertTrue(findFirstFailedRule(signedEnvelope, ECheckSignedByPayerValidityRule.class).isEmpty());
    }

    @Test
    public void test_multiple_envelopes_plus_valid_one() throws Exception {

        ECheck eCheck = eCheckBuilder.build();

        EnvelopeContent innerContent = EnvelopeContent.builder()
                .addEntity(eCheck)
                .build();

        Envelope innerEnvelope = Envelope.builder()
                .addContent(innerContent)
                .build();
        Envelope signedEnvelope = SignatureHelper.sign(innerEnvelope, payerCred, SignatureReason.CONFIRM);

        EnvelopeContent outerContent = EnvelopeContent.builder()
                .addEntity(signedEnvelope)
                .build();

        Envelope outerNoSignatureEnvelope = Envelope.builder()
                .addContent(outerContent)
                .build();

        assertTrue(findFirstFailedRule(outerNoSignatureEnvelope, ECheckSignedByPayerValidityRule.class).isEmpty());
    }

    @Test
    public void test_single_envelope_no_signature() throws Exception {

        ECheck eCheck = eCheckBuilder.build();

        EnvelopeContent content = EnvelopeContent.builder()
                .addEntity(eCheck)
                .build();

        Envelope envelope = Envelope.builder()
                .addContent(content)
                .build();

        assertFalse(findFirstFailedRule(envelope, ECheckSignedByPayerValidityRule.class).isEmpty());
    }
}
