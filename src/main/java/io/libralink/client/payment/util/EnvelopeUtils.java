package io.libralink.client.payment.util;

import com.google.protobuf.Message;
import io.libralink.client.payment.proto.Libralink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.libralink.client.payment.proto.Libralink.EnvelopeContent.EntityCase.*;

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

        Optional<?> entityOptional = getEnvelopeContentEntity(envelope, getEntityCaseByClass(clazz), clazz);

        if (entityOptional.isPresent()) {
            T unpacked = (T) entityOptional.get();
            return Optional.of(unpacked);
        }

        Optional<Libralink.Envelope> envelopeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.ENVELOPE, Libralink.Envelope.class);
        Optional<Libralink.ProcessingFee> processingFeeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.PROCESSINGFEE, Libralink.ProcessingFee.class);
        Optional<Libralink.SurchargeRequest> surchargeRequestOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.SURCHARGEREQUEST, Libralink.SurchargeRequest.class);

        if (envelopeOptional.isPresent()) {
            return findEntityByType(envelopeOptional.get(), clazz);
        }

        if (processingFeeOptional.isPresent()) {
            return findEntityByType(processingFeeOptional.get().getEnvelope(), clazz);
        }

        if (surchargeRequestOptional.isPresent()) {
            return findEntityByType(surchargeRequestOptional.get().getEnvelope(), clazz);
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

        Optional<Libralink.Envelope> envelopeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.ENVELOPE, Libralink.Envelope.class);
        Optional<Libralink.ProcessingFee> processingFeeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.PROCESSINGFEE, Libralink.ProcessingFee.class);
        Optional<Libralink.SurchargeRequest> surchargeRequestOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.SURCHARGEREQUEST, Libralink.SurchargeRequest.class);

        if (envelopeOptional.isPresent()) {
            return findSignedEnvelopeByPub(envelopeOptional.get(), pub);
        }

        if (processingFeeOptional.isPresent()) {
            return findSignedEnvelopeByPub(processingFeeOptional.get().getEnvelope(), pub);
        }

        if (surchargeRequestOptional.isPresent()) {
            return findSignedEnvelopeByPub(surchargeRequestOptional.get().getEnvelope(), pub);
        }

        return Optional.empty();
    }

    public static List<Libralink.Envelope> findAllProcessingDetailsEnvelopes(Libralink.Envelope envelope) throws Exception {

        if (envelope == null) {
            return new ArrayList<>();
        }

        Optional<Libralink.Envelope> envelopeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.ENVELOPE, Libralink.Envelope.class);
        Optional<Libralink.ProcessingFee> processingFeeOptional = getEnvelopeContentEntity(envelope, Libralink.EnvelopeContent.EntityCase.PROCESSINGFEE, Libralink.ProcessingFee.class);
        
        if (envelopeOptional.isPresent()) {
            return findAllProcessingDetailsEnvelopes(envelopeOptional.get());

        } else if (processingFeeOptional.isPresent()) {
            List<Libralink.Envelope> result = new ArrayList<>();
            result.add(envelope);
            result.addAll(findAllProcessingDetailsEnvelopes(processingFeeOptional.get().getEnvelope()));
            return result;

        } else {
            return new ArrayList<>();
        }
    }

    public interface EntityAttributeExtractFunc<T, K> {
        K apply(T entity);
    }

    public static <T> Optional<T> getEnvelopeContentEntity(Libralink.Envelope envelope, Libralink.EnvelopeContent.EntityCase entityCase, Class<T> clazz) {
        Libralink.EnvelopeContent content = envelope.getContent();
        
        if (entityCase.equals(content.getEntityCase())) {
            switch (content.getEntityCase()) {
                case REGISTERKEYRESPONSE:
                    return Optional.of((T) content.getRegisterKeyResponse());
                    
                case REGISTERKEYREQUEST:
                    return Optional.of((T) content.getRegisterKeyRequest());
                    
                case GETPROCESSORSRESPONSE:
                    return Optional.of((T) content.getGetProcessorsResponse());
                    
                case GETPROCESSORSREQUEST:
                    return Optional.of((T) content.getGetProcessorsRequest());
                    
                case DEPOSITRESPONSE:
                    return Optional.of((T) content.getDepositResponse());
                    
                case DEPOSITREQUEST:
                    return Optional.of((T) content.getDepositRequest());
                    
                case GETBALANCEREQUEST:
                    return Optional.of((T) content.getGetBalanceRequest());
                    
                case GETBALANCERESPONSE:
                    return Optional.of((T) content.getGetBalanceResponse());
                    
                case CHECK:
                    return Optional.of((T) content.getCheck());
                    
                case SURCHARGEREQUEST:
                    return Optional.of((T) content.getSurchargeRequest());
                    
                case PROCESSINGFEE:
                    return Optional.of((T) content.getProcessingFee());                
                
                case ENVELOPE:
                    return Optional.of((T) content.getEnvelope());

                case PAYMENTREQUEST:
                    return Optional.of((T) content.getPaymentRequest());

                case ERRORRESPONSE:
                    return Optional.of((T) content.getErrorResponse());

                case ENTITY_NOT_SET:
                    return Optional.empty();

                default:
                    return Optional.empty();
            }
        } else {

            return Optional.empty();
        }
    }

    public static Libralink.EnvelopeContent.EntityCase getEntityCaseByClass(Class<?> clazz) {
        final String simpleClassName = clazz.getSimpleName();
        switch (simpleClassName) {
            case "RegisterKeyResponse":
                return REGISTERKEYRESPONSE;

            case "RegisterKeyRequest":
                return REGISTERKEYREQUEST;

            case "GetProcessorsResponse":
                return GETPROCESSORSRESPONSE;

            case "GetProcessorsRequest":
                return GETPROCESSORSREQUEST;

            case "DepositResponse":
                return DEPOSITRESPONSE;

            case "DepositRequest":
                return DEPOSITREQUEST;

            case "GetBalanceRequest":
                return GETBALANCEREQUEST;

            case "GetBalanceResponse":
                return GETBALANCERESPONSE;

            case "ECheck":
                return CHECK;

            case "SurchargeRequest":
                return SURCHARGEREQUEST;

            case "ProcessingFee":
                return PROCESSINGFEE;

            case "Envelope":
                return ENVELOPE;

            case "PaymentRequest":
                return PAYMENTREQUEST;

            case "ErrorResponse":
                return ERRORRESPONSE;

            default:
                return null;
        }
    }
}
