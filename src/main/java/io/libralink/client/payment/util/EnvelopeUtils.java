package io.libralink.client.payment.util;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import io.libralink.client.payment.proto.Libralink;

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
    public static <T extends Message, K> Optional<K> extractEntityAttribute(
            Libralink.Envelope envelope, Class<T> clazz, EntityAttributeExtractFunc<T, K> extractAttribute) throws Exception {

        if (envelope == null) {
            return Optional.empty();
        }

        Optional<T> entityOptional = findEntityByType(envelope, clazz);
        return entityOptional.map(extractAttribute::apply).stream().findFirst();
    }

    public static <T extends Message> Optional<T> findEntityByType(Libralink.Envelope envelope, Class<T> clazz) throws Exception {

        Any entity = envelope.getContent().getEntity();
        if (entity.is(clazz)) {
            T unpacked = entity.unpack(clazz);
            return Optional.of(unpacked);
        }

        if (entity.is(Libralink.Envelope.class)) {
            return findEntityByType(entity.unpack(Libralink.Envelope.class), clazz);
        }

        if (entity.is(Libralink.ProcessingFee.class)) {
            return findEntityByType(entity.unpack(Libralink.ProcessingFee.class).getEnvelope(), clazz);
        }

        if (entity.is(Libralink.SurchargeRequest.class)) {
            return findEntityByType(entity.unpack(Libralink.SurchargeRequest.class).getEnvelope(), clazz);
        }

        return Optional.empty();
    }

    public static Optional<Libralink.Envelope> findSignedEnvelopeByPub(Libralink.Envelope envelope, String pub) throws Exception {

        if (envelope == null) {
            return Optional.empty();
        }

        String envelopePub = envelope.getContent().getAddress();
        String envelopeSig = envelope.getSig();

        if (envelopeSig != null && envelopePub.equals(pub)) {
            return Optional.of(envelope);
        }

        Any entity = envelope.getContent().getEntity();
        if (entity.is(Libralink.Envelope.class)) {
            return findSignedEnvelopeByPub(entity.unpack(Libralink.Envelope.class), pub);
        }

        if (entity.is(Libralink.ProcessingFee.class)) {
            return findSignedEnvelopeByPub(entity.unpack(Libralink.ProcessingFee.class).getEnvelope(), pub);
        }

        if (entity.is(Libralink.SurchargeRequest.class)) {
            return findSignedEnvelopeByPub(entity.unpack(Libralink.SurchargeRequest.class).getEnvelope(), pub);
        }

        return Optional.empty();
    }

    public static List<Libralink.Envelope> findAllProcessingDetailsEnvelopes(Libralink.Envelope envelope) throws Exception {

        if (envelope == null) {
            return new ArrayList<>();
        }

        Any content = envelope.getContent().getEntity();

        if (content.is(Libralink.Envelope.class)) {
            return findAllProcessingDetailsEnvelopes(content.unpack(Libralink.Envelope.class));

        } else if (content.is(Libralink.ProcessingFee.class)) {
            List<Libralink.Envelope> result = new ArrayList<>();
            result.add(envelope);
            result.addAll(findAllProcessingDetailsEnvelopes(content.unpack(Libralink.ProcessingFee.class).getEnvelope()));
            return result;

        } else {
            return new ArrayList<>();
        }
    }

    public interface EntityAttributeExtractFunc<T, K> {
        K apply(T entity);
    }
}
