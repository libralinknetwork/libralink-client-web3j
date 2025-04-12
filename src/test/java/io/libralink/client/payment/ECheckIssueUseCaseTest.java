package io.libralink.client.payment;

import io.libralink.client.payment.protocol.echeck.ECheck;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.EnvelopeContent;
import io.libralink.client.payment.protocol.envelope.SignatureReason;
import io.libralink.client.payment.protocol.exception.BuilderException;
import io.libralink.client.payment.signature.SignatureHelper;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.math.BigDecimal;
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
        Envelope unsignedEnvelope = createECheckEnvelope(PAYER_ADDR, PROCESSOR_ADDR, PAYEE_ADDR, PROCESSOR_ADDR, BigDecimal.valueOf(150));
        Envelope signedEnvelope = SignatureHelper.sign(unsignedEnvelope, PAYER_CRED, SignatureReason.CONFIRM);

        assertNotNull(signedEnvelope.getSig());
        assertEquals("0x2e37346b9a27ff4f4ecdbcbbd4e33e775d62aefa3d1ae80494f91ca2488e89d97820f4eaaac8152605266d0a513961b40f5dab0048945c5ff67239bdca3fc2b81c", signedEnvelope.getSig());
    }

    public Envelope createECheckEnvelope(String payer, String payerProcessor, String payee, String payeeProcessor, BigDecimal amount) throws BuilderException {

        long createdAtUtc = 1743526954033L;
        long expiresAtUtc = 1843526954033L;

        ECheck eCheck = ECheck.builder()
                .addCreatedAt(createdAtUtc)
                .addExpiresAt(expiresAtUtc)
                .addFaceAmount(amount)
                .addCurrency("USDC")
                .addPayee(payee)
                .addPayeeProcessor(payeeProcessor)
                .addPayer(payer)
                .addPayerProcessor(payerProcessor)
                .addId(UUID.fromString("9eef8f11-2baf-4f7a-8529-38fc20444d88"))
                .build();

        EnvelopeContent content = EnvelopeContent.builder()
                .addEntity(eCheck)
                .build();

        Envelope envelope = Envelope.builder()
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
