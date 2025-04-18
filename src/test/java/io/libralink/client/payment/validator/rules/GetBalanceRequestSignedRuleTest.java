package io.libralink.client.payment.validator.rules;

import com.google.protobuf.Any;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.api.GetBalanceRequestBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.proto.builder.exception.BuilderException;
import io.libralink.client.payment.signature.SignatureHelper;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.util.UUID;

import static io.libralink.client.payment.validator.BaseEntityValidator.findFirstFailedRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetBalanceRequestSignedRuleTest {

    private final String ACCOUNT_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    private final Credentials accountCred = Credentials.create(ACCOUNT_PK);

    private String OTHER_PK = "1c9f40ff758f70b5c59c9df78738fdd122c4f5d6324e61448a3f516b7df00b22";
    private final Credentials otherCred = Credentials.create(OTHER_PK);

    final Libralink.GetBalanceRequest requestInvalidAddress = GetBalanceRequestBuilder.newBuilder()
            .addAddress("0x12345")
            .build();

    final Libralink.GetBalanceRequest requestCorrectAddress = GetBalanceRequestBuilder.newBuilder()
            .addAddress(accountCred.getAddress())
            .build();

    Libralink.EnvelopeContent noSignatureEnvelopeContent = EnvelopeContentBuilder.newBuilder()
            .addEntity(Any.pack(requestCorrectAddress))
            .build();

    final Libralink.Envelope noSignatureEnvelope = EnvelopeBuilder.newBuilder()
            .addId(UUID.randomUUID())
            .addContent(noSignatureEnvelopeContent)
            .build();

    Libralink.EnvelopeContent noSignatureInvalidAddressEnvelopeContent = EnvelopeContentBuilder.newBuilder()
            .addEntity(Any.pack(requestInvalidAddress))
            .build();

    final Libralink.Envelope noSignatureInvalidAddressEnvelope = EnvelopeBuilder.newBuilder()
            .addId(UUID.randomUUID())
            .addContent(noSignatureInvalidAddressEnvelopeContent)
            .build();

    @Test
    public void test_no_signature_present() throws Exception {
        assertFalse(findFirstFailedRule(noSignatureEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    @Test
    public void test_signature_present() throws Exception {

        Libralink.Envelope signedEnvelope = SignatureHelper.sign(noSignatureEnvelope, accountCred, Libralink.SignatureReason.CONFIRM);
        assertTrue(findFirstFailedRule(signedEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    @Test
    public void test_invalid_signature() throws Exception {

        Libralink.Envelope signedEnvelope = SignatureHelper.sign(noSignatureEnvelope, otherCred, Libralink.SignatureReason.CONFIRM);
        assertFalse(findFirstFailedRule(signedEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    @Test
    public void test_invalid_address() throws Exception {

        Libralink.Envelope signedEnvelope = SignatureHelper.sign(noSignatureInvalidAddressEnvelope, accountCred, Libralink.SignatureReason.CONFIRM);
        assertFalse(findFirstFailedRule(signedEnvelope, GetBalanceRequestSignedRule.class).isEmpty());
    }

    public GetBalanceRequestSignedRuleTest() throws BuilderException {
    }
}
