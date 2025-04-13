package io.libralink.client.payment.signature;

import io.libralink.client.payment.protocol.SignatureAlgorithm;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.EnvelopeContent;
import io.libralink.client.payment.protocol.envelope.SignatureReason;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;
import io.libralink.client.payment.util.EncryptionUtils;
import io.libralink.client.payment.util.JsonUtils;
import org.web3j.crypto.Credentials;

import static io.libralink.client.payment.util.EncryptionUtils.recover;

public final class SignatureHelper {

    private SignatureHelper() {}

    public static Envelope sign(Envelope envelope, Credentials credentials, SignatureReason reason) throws Exception {
        EnvelopeContent content = envelope.getContent();
        EnvelopeContent signedContent = EnvelopeContent.builder(content)
                .addSigReason(reason)
                .addAddress(credentials.getAddress())
                .addAlgorithm(SignatureAlgorithm.SECP256K1)
                .build();

        String json = JsonUtils.toJson(signedContent);
        String sig = EncryptionUtils.sign(json, credentials);

        return Envelope.builder(envelope)
            .addSig(sig)
            .addContent(signedContent)
                .build();
    }

    public static boolean verify(Envelope envelope) throws Exception {
        if (envelope == null || envelope.getContent() == null) {
            return false;
        }

        EnvelopeContent content = envelope.getContent();
        String sig = envelope.getSig();
        String pub = content.getAddress();
        String json = JsonUtils.toJson(content);

        boolean isValid = recover(json, sig, pub);

        if (ProcessingDetails.class.equals(content.getEntity().getClass())) {
            ProcessingDetails details = (ProcessingDetails) content.getEntity();
            isValid = isValid && verify(details.getEnvelope());
        }

        if (Envelope.class.equals(content.getEntity().getClass())) {
            isValid = isValid && verify((Envelope) content.getEntity());
        }

        return isValid;
    }
}
