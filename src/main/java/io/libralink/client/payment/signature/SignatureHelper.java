package io.libralink.client.payment.signature;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.Signature;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;
import io.libralink.client.payment.util.EncryptionUtils;
import io.libralink.client.payment.util.JsonUtils;
import org.web3j.crypto.Credentials;

import static io.libralink.client.payment.util.EncryptionUtils.recover;

public final class SignatureHelper {

    private SignatureHelper() {}

    public static Envelope sign(Envelope envelope, Credentials credentials) throws Exception {
        AbstractEntity content =  envelope.getContent();
        String json = JsonUtils.toJson(content);
        String sig = EncryptionUtils.sign(json, credentials);

        Signature signature = Signature.builder()
            .addPub(credentials.getAddress())
            .addSig(sig)
                .build();

        return Envelope.builder(envelope)
            .addSignature(signature)
                .build();
    }

    public static boolean verify(Envelope envelope) throws Exception {
        if (envelope == null || envelope.getContent() == null) {
            return false;
        }

        AbstractEntity content =  envelope.getContent();
        Signature signature = envelope.getSignature();
        String json = JsonUtils.toJson(content);

        boolean isValid = recover(json, signature.getSig(), signature.getPub());

        if (ProcessingDetails.class.equals(content.getClass())) {
            ProcessingDetails details = (ProcessingDetails) content;
            isValid = isValid && verify(details.getEnvelope());
        }

        if (Envelope.class.equals(content.getClass())) {
            isValid = isValid && verify((Envelope) content);
        }

        return isValid;
    }
}
