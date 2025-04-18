package io.libralink.client.payment;

import com.google.protobuf.Any;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.echeck.ECheckBuilder;
import io.libralink.client.payment.proto.builder.echeck.ECheckSplitBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.proto.builder.exception.BuilderException;
import io.libralink.client.payment.signature.SignatureHelper;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ECheckIssueUseCaseTest {

    /* Payer Credentials */
    final private String PAYER_ADDR = "0xf39902b133fbdcf926c1f48665c98d1b028d905a";
    final private String PAYER_PK = "7af8df13f6aebcbd9edd369bb5f67bf7523517685491fea776bb547910ff5673";
    final private Credentials PAYER_CRED = Credentials.create(PAYER_PK);

    /* PAYEE */
    final private String PAYEE_ADDR = "0x8f33dceeedfcf7185aa480ee16db9b9bb745756e";

    /* Processor Credentials */
    private String PROCESSOR_ADDR = "0x127cc4d943dff0a4bd6b024a96554a84e6247440";
    private String PROCESSOR_PK = "1c9f40ff758f70b5c59c9df78738fdd122c4f5d6324e61448a3f516b7df00b22";

    @Test
    public void test_issue_echeck() throws Exception {

        /* 1. Payee creates E-Check and signs it */
        Libralink.Envelope unsignedEnvelope = createECheckEnvelope(PAYER_ADDR, PROCESSOR_ADDR, PAYEE_ADDR, PROCESSOR_ADDR, BigDecimal.valueOf(150));
        Libralink.Envelope signedEnvelope = SignatureHelper.sign(unsignedEnvelope, PAYER_CRED, Libralink.SignatureReason.CONFIRM);

        assertNotNull(signedEnvelope.getSig());
        assertEquals("0x143847d584b0aaee7b0fdeaec6e9e5b264093bb56413d72a484e16a81a0a3f635d1fe8c2aac5ea4884597f3b8d889dfa8ec41f24790ded21e3ba98fdc17fdcc81c", signedEnvelope.getSig());
    }

    public Libralink.Envelope createECheckEnvelope(String payer, String payerProcessor, String payee, String payeeProcessor, BigDecimal amount) throws BuilderException {

        long createdAtUtc = 1743526954033L;
        long expiresAtUtc = 1843526954033L;

        Libralink.ECheck eCheck = ECheckBuilder.newBuilder()
                .addCorrelationId(UUID.fromString("8ee509de-743f-45da-8815-a64416ec612e"))
                .addCreatedAt(createdAtUtc)
                .addExpiresAt(expiresAtUtc)
                .addFaceAmount(amount)
                .addCurrency("USDC")
                .addTo(payee)
                .addToProc(payeeProcessor)
                .addFrom(payer)
                .addFromProc(payerProcessor)
                .addSplits(List.of(ECheckSplitBuilder.newBuilder()
                        .addTo(payee)
                        .addToProc(payeeProcessor)
                        .addAmount(amount)
                        .build()))
                .build();

        Libralink.EnvelopeContent content = EnvelopeContentBuilder.newBuilder()
                .addEntity(Any.pack(eCheck))
                .build();

        Libralink.Envelope envelope = EnvelopeBuilder.newBuilder()
                .addContent(content)
                .addId(UUID.fromString("e2a3eecd-b99e-4c8f-b3c9-01aacb73a1a4"))
                .build();

        return envelope;
    }

    @Test
    public void test_create_key_pair() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        Credentials credentials = Credentials.create(ecKeyPair);

        System.out.println(credentials.getAddress());
        System.out.println(credentials.getEcKeyPair().getPrivateKey().toString(16));
    }
}
