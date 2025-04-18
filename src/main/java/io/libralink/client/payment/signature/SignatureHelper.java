package io.libralink.client.payment.signature;

import com.google.protobuf.Any;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.util.EncryptionUtils;
import org.web3j.crypto.Credentials;

import static io.libralink.client.payment.util.EncryptionUtils.recover;

public final class SignatureHelper {

    private SignatureHelper() {}

    public static Libralink.Envelope sign(Libralink.Envelope envelope, Credentials credentials, Libralink.SignatureReason reason) throws Exception {
        Libralink.EnvelopeContent content = envelope.getContent();
        Libralink.EnvelopeContent signedContent = EnvelopeContentBuilder.newBuilder(content)
                .addSigReason(reason)
                .addAddress(credentials.getAddress())
                .addAlgorithm("SECP256K1")
                .build();

        String sig = EncryptionUtils.sign(signedContent.toByteArray(), credentials);

        return EnvelopeBuilder.newBuilder(envelope)
            .addSig(sig)
            .addContent(signedContent)
                .build();
    }

    public static boolean verify(Libralink.Envelope envelope) throws Exception {
        if (envelope == null) {
            return false;
        }

        Libralink.EnvelopeContent content = envelope.getContent();
        String sig = envelope.getSig();
        String pub = content.getAddress();

        boolean isValid = recover(content.toByteArray(), sig, pub);

        Any entity = content.getEntity();
        if (entity.is(Libralink.ProcessingFee.class)) {
            Libralink.ProcessingFee details = entity.unpack(Libralink.ProcessingFee.class);
            isValid = isValid && verify(details.getEnvelope());
        }

        if (entity.is(Libralink.SurchargeRequest.class)) {
            Libralink.SurchargeRequest surcharge = entity.unpack(Libralink.SurchargeRequest.class);
            isValid = isValid && verify(surcharge.getEnvelope());
        }

        if (entity.is(Libralink.Envelope.class)) {
            isValid = isValid && verify(entity.unpack(Libralink.Envelope.class));
        }

        return isValid;
    }
}
