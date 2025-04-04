package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.echeck.ECheck;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.Signature;
import io.libralink.client.payment.protocol.exception.BuilderException;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;
import io.libralink.client.payment.protocol.processing.ProcessingFee;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.util.UUID;

import static io.libralink.client.payment.validator.BaseEntityValidator.findFirstFailedRule;
import static org.junit.Assert.*;

public class ECheckSingleProcessorOnlyValidityRuleTest {

    final String PROCESSOR_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    final Credentials processorCred = Credentials.create(PROCESSOR_PK);

    final ECheck.Builder eCheckBuilder = ECheck.builder()
            .addPayer("fake")
            .addPayerProcessor("fake")
            .addPayee("fake")
            .addPayeeProcessor(processorCred.getAddress())
            .addCurrency("USDC")
            .addCreatedAt(1743526954033L)
            .addExpiresAt(2743526954133L)
            .addFaceAmount(BigDecimal.valueOf(150));

    final ProcessingFee fee = ProcessingFee.builder()
            .addFeeType("flat").addAmount(BigDecimal.valueOf(5)).build();

    ProcessingDetails.Builder detailsBuilderNoIntermediary = ProcessingDetails.builder()
        .addFee(fee);

    ProcessingDetails.Builder detailsBuilder = ProcessingDetails.builder()
            .addIntermediary(UUID.randomUUID().toString())
            .addFee(fee);

    @Test
    public void test_multiple_processors() throws Exception {

        Envelope eCheckEnvelope = Envelope.builder()
                .addContent(eCheckBuilder.build())
                .build();

        assertFalse(findFirstFailedRule(eCheckEnvelope, ECheckSingleProcessorOnlyValidityRule.class).isEmpty());
    }

    @Test
    public void test_single_processor_with_intermediary() throws Exception {

        Envelope eCheckEnvelope = Envelope.builder()
                .addContent(eCheckBuilder.addPayeeProcessor("fake").build())
                .build();

        ProcessingDetails processingDetails = detailsBuilder.addEnvelope(eCheckEnvelope).build();
        Envelope processingDetailsEnvelope = Envelope.builder()
                .addContent(processingDetails)
                .addSignature(Signature.builder().addPub("fake").addSig("fake").build())
                .build();

        assertFalse(findFirstFailedRule(processingDetailsEnvelope, ECheckSingleProcessorOnlyValidityRule.class).isEmpty());
    }

    @Test
    public void test_single_processor_no_intermediary() throws Exception {

        Envelope eCheckEnvelope = Envelope.builder()
                .addContent(eCheckBuilder.addPayeeProcessor("fake").build())
                .build();

        ProcessingDetails processingDetails = detailsBuilderNoIntermediary.addEnvelope(eCheckEnvelope).build();
        Envelope processingDetailsEnvelope = Envelope.builder()
                .addContent(processingDetails)
                .addSignature(Signature.builder().addPub("fake").addSig("fake").build())
                .build();

        assertTrue(findFirstFailedRule(processingDetailsEnvelope, ECheckSingleProcessorOnlyValidityRule.class).isEmpty());
    }

    public ECheckSingleProcessorOnlyValidityRuleTest() throws BuilderException {
    }
}
