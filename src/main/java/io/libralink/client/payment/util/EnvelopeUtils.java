package io.libralink.client.payment.util;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.Signature;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class EnvelopeUtils {

    private EnvelopeUtils() {}

    /**
     * If we are looking for payer, then Envelope must contain either ECheck or DepositApproval
     * @param envelope Envelope leading to ECheck or DepositApproval
     * @return <b>payee pub</b> if found
     */
    public static <T, K> Optional<K> extractEntityAttribute(
            Envelope envelope, Class<T> clazz, EntityAttributeExtractFunc<T, K> extractAttribute) {

        if (envelope == null) {
            return Optional.empty();
        }

        Optional<AbstractEntity> entityOptional = findEntityByType(envelope, clazz);
        return entityOptional.map(entity -> extractAttribute.apply((T) entity)).stream().findFirst();
    }

    private static <T> Optional<AbstractEntity> findEntityByType(Envelope envelope, Class<T> clazz) {

        AbstractEntity content = envelope.getContent();
        if (content.getClass().equals(clazz)) {
            return Optional.of(content);
        }

        if (Envelope.class.equals(content.getClass())) {
            return findEntityByType((Envelope) content, clazz);
        }

        if (ProcessingDetails.class.equals(content.getClass())) {
            return findEntityByType(((ProcessingDetails) content).getEnvelope(), clazz);
        }

        return Optional.empty();
    }

    public static Optional<Envelope> findSignedEnvelopeByPub(Envelope envelope, String pub) {

        if (envelope == null) {
            return Optional.empty();
        }

        Signature signature = envelope.getSignature();
        if (signature != null && signature.getPub().equals(pub)) {
            return Optional.of(envelope);
        }

        if (Envelope.class.equals(envelope.getContent().getClass())) {
            return findSignedEnvelopeByPub((Envelope) envelope.getContent(), pub);
        }

        if (ProcessingDetails.class.equals(envelope.getContent().getClass())) {
            return findSignedEnvelopeByPub(((ProcessingDetails) envelope.getContent()).getEnvelope(), pub);
        }

        return Optional.empty();
    }

    public static List<Envelope> findAllProcessingDetailsEnvelopes(Envelope envelope) {

        if (envelope == null) {
            return new ArrayList<>();
        }

        AbstractEntity content = envelope.getContent();

        if (Envelope.class.equals(content.getClass())) {
            return findAllProcessingDetailsEnvelopes(((Envelope) content));

        } else if (ProcessingDetails.class.equals(content.getClass())) {
            List<Envelope> result = new ArrayList<>();
            result.add(envelope);
            result.addAll(findAllProcessingDetailsEnvelopes(((ProcessingDetails) content).getEnvelope()));
            return result;

        } else {
            return new ArrayList<>();
        }
    }

    public interface EntityAttributeExtractFunc<T, K> {
        K apply(T entity);
    }
}
