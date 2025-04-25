package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.echeck.ECheckBuilder;
import io.libralink.client.payment.proto.builder.echeck.ECheckSplitBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.proto.builder.fee.ProcessingFeeBuilder;
import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static io.libralink.client.payment.validator.BaseEntityValidator.findFirstFailedRule;
import static org.junit.Assert.*;

public class ECheckSingleProcessorOnlyValidityRuleTest {

    final String PROCESSOR_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    final Credentials processorCred = Credentials.create(PROCESSOR_PK);

    final ECheckBuilder eCheckBuilder = ECheckBuilder.newBuilder()
            .addCorrelationId(UUID.fromString("e3b73203-17cb-437b-a4ba-22ae9047bb39"))
            .addFrom("fake")
            .addFromProc("fake")
            .addTo("fake")
            .addToProc(processorCred.getAddress())
            .addCurrency("USDC")
            .addCreatedAt(1743526954033L)
            .addExpiresAt(2743526954133L)
            .addFaceAmount(BigDecimal.valueOf(150))
            .addSplits(List.of(ECheckSplitBuilder.newBuilder()
                    .addTo("fake")
                    .addToProc("fake")
                    .addAmount(BigDecimal.valueOf(150))
                    .build()));

    final ProcessingFeeBuilder feeBuilderNoIntermediary = ProcessingFeeBuilder.newBuilder()
            .addFeeType("flat").addAmount(BigDecimal.valueOf(5));

    final ProcessingFeeBuilder feeBuilder = ProcessingFeeBuilder.newBuilder()
            .addIntermediary(UUID.randomUUID().toString())
            .addFeeType("flat").addAmount(BigDecimal.valueOf(5));

    @Test
    public void test_multiple_processors() throws Exception {

        Libralink.EnvelopeContent envelopeContent = EnvelopeContentBuilder.newBuilder()
                .addECheck(eCheckBuilder.build())
                .addAddress("fake")
                .addSigReason(Libralink.SignatureReason.CONFIRM)
                .build();

        Libralink.Envelope eCheckEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(envelopeContent)
                .build();

        assertFalse(findFirstFailedRule(eCheckEnvelope, ECheckSingleProcessorOnlyValidityRule.class).isEmpty());
    }

    @Test
    public void test_single_processor_with_intermediary() throws Exception {

        Libralink.EnvelopeContent envelopeContent = EnvelopeContentBuilder.newBuilder()
                .addECheck(eCheckBuilder.addToProc("fake").build())
                .addAddress("fake")
                .addSigReason(Libralink.SignatureReason.CONFIRM)
                .build();

        Libralink.Envelope eCheckEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(envelopeContent)
                .build();

        Libralink.ProcessingFee processingFee = feeBuilder.addEnvelope(eCheckEnvelope).build();
        Libralink.EnvelopeContent processingDetailsContent = EnvelopeContentBuilder.newBuilder()
                .addProcessingFee(processingFee)
                .addAddress("fake")
                .addSigReason(Libralink.SignatureReason.CONFIRM)
                .build();

        Libralink.Envelope processingDetailsEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(processingDetailsContent)
                .addSig("fake")
                .build();

        assertFalse(findFirstFailedRule(processingDetailsEnvelope, ECheckSingleProcessorOnlyValidityRule.class).isEmpty());
    }

    @Test
    public void test_single_processor_no_intermediary() throws Exception {

        Libralink.EnvelopeContent envelopeContent = EnvelopeContentBuilder.newBuilder()
                .addECheck(eCheckBuilder.addToProc("fake").build())
                .addAddress("fake")
                .addSigReason(Libralink.SignatureReason.CONFIRM)
                .build();

        Libralink.Envelope eCheckEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(envelopeContent)
                .build();

        Libralink.ProcessingFee processingFee = feeBuilderNoIntermediary.addEnvelope(eCheckEnvelope).build();
        Libralink.EnvelopeContent processingDetailsContent = EnvelopeContentBuilder.newBuilder()
                .addProcessingFee(processingFee)
                .addAddress("fake")
                .addSigReason(Libralink.SignatureReason.CONFIRM)
                .build();

        Libralink.Envelope processingDetailsEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(processingDetailsContent)
                .addSig("fake")
                .build();

        assertTrue(findFirstFailedRule(processingDetailsEnvelope, ECheckSingleProcessorOnlyValidityRule.class).isEmpty());
    }

    public ECheckSingleProcessorOnlyValidityRuleTest() throws BuilderException {
    }
}
