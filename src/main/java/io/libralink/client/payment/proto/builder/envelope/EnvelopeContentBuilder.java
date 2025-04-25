package io.libralink.client.payment.proto.builder.envelope;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import static io.libralink.client.payment.proto.Libralink.EnvelopeContent.EntityCase.*;

public final class EnvelopeContentBuilder {

    private Libralink.RegisterKeyRequest entityRegisterKeyRequest;
    private Libralink.RegisterKeyResponse entityRegisterKeyResponse;
    private Libralink.GetProcessorsRequest entityGetProcessorsRequest;
    private Libralink.GetProcessorsResponse entityGetProcessorsResponse;
    private Libralink.DepositRequest entityDepositRequest;
    private Libralink.DepositResponse entityDepositResponse;
    private Libralink.GetBalanceRequest entityGetBalanceRequest;
    private Libralink.GetBalanceResponse entityGetBalanceResponse;
    private Libralink.ECheck entityECheck;
    private Libralink.SurchargeRequest entitySurchargeRequest;
    private Libralink.ProcessingFee entityProcessingFee;
    private Libralink.Envelope entityEnvelope;
    private Libralink.PaymentRequest entityPaymentRequest;
    private Libralink.ErrorResponse entityErrorResponse;

    private String address;
    private String pubKey;
    private Libralink.SignatureReason sigReason;
    private String algorithm;

    private EnvelopeContentBuilder() {
    }

    private EnvelopeContentBuilder(Libralink.EnvelopeContent content) {

        this.entityRegisterKeyRequest = REGISTERKEYREQUEST.equals(content.getEntityCase()) ? content.getRegisterKeyRequest(): null;
        this.entityRegisterKeyResponse = REGISTERKEYRESPONSE.equals(content.getEntityCase()) ? content.getRegisterKeyResponse(): null;
        this.entityGetProcessorsRequest = GETPROCESSORSREQUEST.equals(content.getEntityCase()) ? content.getGetProcessorsRequest(): null;
        this.entityGetProcessorsResponse = GETPROCESSORSRESPONSE.equals(content.getEntityCase()) ? content.getGetProcessorsResponse(): null;
        this.entityDepositRequest = DEPOSITREQUEST.equals(content.getEntityCase()) ? content.getDepositRequest(): null;
        this.entityDepositResponse = DEPOSITRESPONSE.equals(content.getEntityCase()) ? content.getDepositResponse(): null;
        this.entityGetBalanceRequest = GETBALANCEREQUEST.equals(content.getEntityCase()) ? content.getGetBalanceRequest(): null;
        this.entityGetBalanceResponse = GETBALANCERESPONSE.equals(content.getEntityCase()) ? content.getGetBalanceResponse(): null;
        this.entityECheck = CHECK.equals(content.getEntityCase()) ? content.getCheck(): null;
        this.entitySurchargeRequest = SURCHARGEREQUEST.equals(content.getEntityCase()) ? content.getSurchargeRequest(): null;
        this.entityProcessingFee = PROCESSINGFEE.equals(content.getEntityCase()) ? content.getProcessingFee(): null;
        this.entityEnvelope = ENVELOPE.equals(content.getEntityCase()) ? content.getEnvelope(): null;
        this.entityPaymentRequest = PAYMENTREQUEST.equals(content.getEntityCase()) ? content.getPaymentRequest(): null;
        this.entityErrorResponse = ERRORRESPONSE.equals(content.getEntityCase()) ? content.getErrorResponse(): null;

        this.address = content.getAddress();
        this.pubKey = content.getPubKey();
        this.sigReason = content.getReason();
        this.algorithm = content.getAlgorithm();
    }

    public static EnvelopeContentBuilder newBuilder() {
        return new EnvelopeContentBuilder();
    }

    public static EnvelopeContentBuilder newBuilder(Libralink.EnvelopeContent content) {
        return new EnvelopeContentBuilder(content);
    }

    public EnvelopeContentBuilder addErrorResponse(Libralink.ErrorResponse entityErrorResponse) {
        this.entityErrorResponse = entityErrorResponse;
        return this;
    }

    public EnvelopeContentBuilder addPaymentRequest(Libralink.PaymentRequest entityPaymentRequest) {
        this.entityPaymentRequest = entityPaymentRequest;
        return this;
    }

    public EnvelopeContentBuilder addEnvelope(Libralink.Envelope entityEnvelope) {
        this.entityEnvelope = entityEnvelope;
        return this;
    }

    public EnvelopeContentBuilder addProcessingFee(Libralink.ProcessingFee entityProcessingFee) {
        this.entityProcessingFee = entityProcessingFee;
        return this;
    }

    public EnvelopeContentBuilder addSurchargeRequest(Libralink.SurchargeRequest entitySurchargeRequest) {
        this.entitySurchargeRequest = entitySurchargeRequest;
        return this;
    }

    public EnvelopeContentBuilder addECheck(Libralink.ECheck entityECheck) {
        this.entityECheck = entityECheck;
        return this;
    }

    public EnvelopeContentBuilder addGetBalanceResponse(Libralink.GetBalanceResponse entityGetBalanceResponse) {
        this.entityGetBalanceResponse = entityGetBalanceResponse;
        return this;
    }

    public EnvelopeContentBuilder addGetBalanceRequest(Libralink.GetBalanceRequest entityGetBalanceRequest) {
        this.entityGetBalanceRequest = entityGetBalanceRequest;
        return this;
    }

    public EnvelopeContentBuilder addDepositResponse(Libralink.DepositResponse entityDepositResponse) {
        this.entityDepositResponse = entityDepositResponse;
        return this;
    }

    public EnvelopeContentBuilder addDepositRequest(Libralink.DepositRequest entityDepositRequest) {
        this.entityDepositRequest = entityDepositRequest;
        return this;
    }

    public EnvelopeContentBuilder addGetProcessorsResponse(Libralink.GetProcessorsResponse entityGetProcessorsResponse) {
        this.entityGetProcessorsResponse = entityGetProcessorsResponse;
        return this;
    }

    public EnvelopeContentBuilder addGetProcessorsRequest(Libralink.GetProcessorsRequest entityGetProcessorsRequest) {
        this.entityGetProcessorsRequest = entityGetProcessorsRequest;
        return this;
    }

    public EnvelopeContentBuilder addRegisterKeyResponse(Libralink.RegisterKeyResponse entityRegisterKeyResponse) {
        this.entityRegisterKeyResponse = entityRegisterKeyResponse;
        return this;
    }

    public EnvelopeContentBuilder addRegisterKeyRequest(Libralink.RegisterKeyRequest entityRegisterKeyRequest) {
        this.entityRegisterKeyRequest = entityRegisterKeyRequest;
        return this;
    }

    public EnvelopeContentBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    public EnvelopeContentBuilder addPubKey(String pubKey) {
        this.pubKey = pubKey;
        return this;
    }

    public EnvelopeContentBuilder addAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public EnvelopeContentBuilder addSigReason(Libralink.SignatureReason sigReason) {
        this.sigReason = sigReason;
        return this;
    }

    public Libralink.EnvelopeContent build() throws BuilderException {

        if (entityRegisterKeyRequest == null && entityRegisterKeyResponse == null && entityGetProcessorsRequest == null
            && entityGetProcessorsResponse == null && entityDepositRequest == null && entityDepositResponse == null
            && entityGetBalanceRequest == null && entityGetBalanceResponse == null && entityECheck == null
            && entitySurchargeRequest == null && entityProcessingFee == null && entityEnvelope == null
            && entityPaymentRequest == null && entityErrorResponse == null) {

            throw new BuilderException();
        }

        if ("DILITHIUM5".equals(algorithm) && pubKey == null) {
            throw new BuilderException();
        }

        Libralink.EnvelopeContent.Builder builder = Libralink.EnvelopeContent.newBuilder();

        if (entityRegisterKeyRequest != null) builder.setRegisterKeyRequest(entityRegisterKeyRequest);
        if (entityRegisterKeyResponse != null) builder.setRegisterKeyResponse(entityRegisterKeyResponse);
        if (entityGetProcessorsRequest != null) builder.setGetProcessorsRequest(entityGetProcessorsRequest);
        if (entityGetProcessorsResponse != null) builder.setGetProcessorsResponse(entityGetProcessorsResponse);
        if (entityDepositRequest != null) builder.setDepositRequest(entityDepositRequest);
        if (entityDepositResponse != null) builder.setDepositResponse(entityDepositResponse);
        if (entityGetBalanceRequest != null) builder.setGetBalanceRequest(entityGetBalanceRequest);
        if (entityGetBalanceResponse != null) builder.setGetBalanceResponse(entityGetBalanceResponse);
        if (entityECheck != null) builder.setCheck(entityECheck);
        if (entitySurchargeRequest != null) builder.setSurchargeRequest(entitySurchargeRequest);
        if (entityProcessingFee != null) builder.setProcessingFee(entityProcessingFee);
        if (entityEnvelope != null) builder.setEnvelope(entityEnvelope);
        if (entityPaymentRequest != null) builder.setPaymentRequest(entityPaymentRequest);
        if (entityErrorResponse != null) builder.setErrorResponse(entityErrorResponse);

        if (address != null) builder.setAddress(address);
        if (algorithm != null) builder.setAlgorithm(algorithm);
        if (pubKey != null) builder.setPubKey(pubKey);
        if (sigReason != null) builder.setReason(sigReason);

        return builder.build();
    }
}
