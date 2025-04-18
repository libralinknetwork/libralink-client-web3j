package io.libralink.client.payment.validator.rules;

import com.google.protobuf.Any;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.echeck.ECheckBuilder;
import io.libralink.client.payment.proto.builder.echeck.ECheckSplitBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.proto.builder.exception.BuilderException;
import io.libralink.client.payment.signature.SignatureHelper;
import static io.libralink.client.payment.validator.BaseEntityValidator.findFirstFailedRule;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ECheckSignedByPayerPartyValidityRuleTest {

    final String PAYER_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    final Credentials payerCred = Credentials.create(PAYER_PK);

    final ECheckBuilder eCheckBuilder = ECheckBuilder.newBuilder()
            .addCorrelationId(UUID.fromString("8fe00676-d973-4310-b620-8ab3d54dee93"))
            .addFrom(payerCred.getAddress())
            .addFromProc("fake")
            .addTo("fake")
            .addToProc("fake")
            .addCurrency("USDC")
            .addCreatedAt(1743526954033L)
            .addExpiresAt(2743526954133L)
            .addFaceAmount(BigDecimal.valueOf(150))
            .addSplits(List.of(ECheckSplitBuilder.newBuilder()
                .addTo("fake")
                .addToProc("fake")
                .addAmount(BigDecimal.valueOf(150))
                .build()));

    public ECheckSignedByPayerPartyValidityRuleTest() throws BuilderException {
    }

    @Test
    public void test_single_envelope_valid() throws Exception {

        Libralink.ECheck eCheck = eCheckBuilder.build();

        Libralink.EnvelopeContent content = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(eCheck))
                .build();

        Libralink.Envelope envelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(content)
                .build();

        Libralink.Envelope signedEnvelope = SignatureHelper.sign(envelope, payerCred, Libralink.SignatureReason.CONFIRM);
        assertTrue(findFirstFailedRule(signedEnvelope, ECheckSignedByPayerValidityRule.class).isEmpty());
    }

    @Test
    public void test_multiple_envelopes_plus_valid_one() throws Exception {

        Libralink.ECheck eCheck = eCheckBuilder.build();

        Libralink.EnvelopeContent innerContent = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(eCheck))
                .build();

        Libralink.Envelope innerEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(innerContent)
                .build();

        Libralink.Envelope signedEnvelope = SignatureHelper.sign(innerEnvelope, payerCred, Libralink.SignatureReason.CONFIRM);

        Libralink.EnvelopeContent outerContent = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(signedEnvelope))
                .build();

        Libralink.Envelope outerNoSignatureEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(outerContent)
                .build();

        assertTrue(findFirstFailedRule(outerNoSignatureEnvelope, ECheckSignedByPayerValidityRule.class).isEmpty());
    }

    @Test
    public void test_single_envelope_no_signature() throws Exception {

        Libralink.ECheck eCheck = eCheckBuilder.build();

        Libralink.EnvelopeContent content = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(eCheck))
                .build();

        Libralink.Envelope envelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(content)
                .build();

        assertFalse(findFirstFailedRule(envelope, ECheckSignedByPayerValidityRule.class).isEmpty());
    }
}
