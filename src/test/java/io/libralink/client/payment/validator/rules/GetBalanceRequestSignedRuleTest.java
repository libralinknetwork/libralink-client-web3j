package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.api.balance.GetBalanceRequest;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.EnvelopeContent;
import io.libralink.client.payment.protocol.envelope.SignatureReason;
import io.libralink.client.payment.protocol.exception.BuilderException;
import io.libralink.client.payment.signature.SignatureHelper;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import static io.libralink.client.payment.validator.BaseEntityValidator.findFirstFailedRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetBalanceRequestSignedRuleTest {

    private final String ACCOUNT_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    private final Credentials accountCred = Credentials.create(ACCOUNT_PK);

    private String OTHER_PK = "1c9f40ff758f70b5c59c9df78738fdd122c4f5d6324e61448a3f516b7df00b22";
    private final Credentials otherCred = Credentials.create(OTHER_PK);

    final GetBalanceRequest requestInvalidAddress = GetBalanceRequest.builder()
            .addAddress("0x12345")
            .build();

    final GetBalanceRequest requestCorrectAddress = GetBalanceRequest.builder()
            .addAddress(accountCred.getAddress())
            .build();

    EnvelopeContent noSignatureEnvelopeContent = EnvelopeContent.builder()
            .addEntity(requestCorrectAddress)
            .build();

    final Envelope noSignatureEnvelope = Envelope.builder()
            .addContent(noSignatureEnvelopeContent)
            .build();

    EnvelopeContent noSignatureInvalidAddressEnvelopeContent = EnvelopeContent.builder()
            .addEntity(requestInvalidAddress)
            .build();

    final Envelope noSignatureInvalidAddressEnvelope = Envelope.builder()
            .addContent(noSignatureInvalidAddressEnvelopeContent)
            .build();

    @Test
    public void test_no_signature_present() throws Exception {
        assertFalse(findFirstFailedRule(noSignatureEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    @Test
    public void test_signature_present() throws Exception {

        Envelope signedEnvelope = SignatureHelper.sign(noSignatureEnvelope, accountCred, SignatureReason.CONFIRM);
        assertTrue(findFirstFailedRule(signedEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    @Test
    public void test_invalid_signature() throws Exception {

        Envelope signedEnvelope = SignatureHelper.sign(noSignatureEnvelope, otherCred, SignatureReason.CONFIRM);
        assertFalse(findFirstFailedRule(signedEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    @Test
    public void test_invalid_address() throws Exception {

        Envelope signedEnvelope = SignatureHelper.sign(noSignatureInvalidAddressEnvelope, accountCred, SignatureReason.CONFIRM);
        assertFalse(findFirstFailedRule(signedEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    public GetBalanceRequestSignedRuleTest() throws BuilderException {
    }
}
