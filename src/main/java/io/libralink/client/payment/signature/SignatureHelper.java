package io.libralink.client.payment.signature;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.util.EncryptionUtils;
import org.web3j.crypto.Credentials;

import java.util.Optional;

import static io.libralink.client.payment.util.EncryptionUtils.recover;
import static io.libralink.client.payment.util.EnvelopeUtils.getEnvelopeContentEntity;

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

        Optional<Libralink.Envelope> envelopeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.ENVELOPE, Libralink.Envelope.class);
        Optional<Libralink.ProcessingFee> processingFeeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.PROCESSINGFEE, Libralink.ProcessingFee.class);
        Optional<Libralink.SurchargeRequest> surchargeRequestOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.SURCHARGEREQUEST, Libralink.SurchargeRequest.class);

        if (processingFeeOptional.isPresent()) {
            Libralink.ProcessingFee details = processingFeeOptional.get();
            isValid = isValid && verify(details.getEnvelope());
        }

        if (surchargeRequestOptional.isPresent()) {
            Libralink.SurchargeRequest surcharge = surchargeRequestOptional.get();
            isValid = isValid && verify(surcharge.getEnvelope());
        }

        if (envelopeOptional.isPresent()) {
            isValid = isValid && verify(envelopeOptional.get());
        }

        return isValid;
    }
}
