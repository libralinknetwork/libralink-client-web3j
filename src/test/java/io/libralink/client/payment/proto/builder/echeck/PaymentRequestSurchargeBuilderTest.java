package io.libralink.client.payment.proto.builder.echeck;

import com.google.protobuf.Any;
import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.signature.SignatureHelper;
import io.libralink.client.payment.util.JsonUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class PaymentRequestSurchargeBuilderTest {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentRequestSurchargeBuilderTest.class);

    /* Payer Credentials */
    final private String PAYER_PK = "7af8df13f6aebcbd9edd369bb5f67bf7523517685491fea776bb547910ff5673";
    final private Credentials PAYER_CRED = Credentials.create(PAYER_PK);

    /* PAYEE */
    final private String PAYEE_PK = "64496cc969654b231087af38ce654aa8d539fea0970d90366e42a5e39761f3f5";
    final private Credentials PAYEE_CRED = Credentials.create(PAYEE_PK);

    /* Intermediary Credentials */
    private String INTERMEDIARY_PK = "3327b0eb87d731bd56594beb3e5ae3b91b81115f03a544a924016c8d0e5476cb";
    final private Credentials INTERMEDIARY_CRED = Credentials.create(INTERMEDIARY_PK);

    /* Processor Credentials */
    private String PROCESSOR_PK = "d601b629b288ce5ab659b4782e7f34cc2279ac729485302fdcc19d0fccb78b0d";
    final private Credentials PROCESSOR_CRED = Credentials.create(PROCESSOR_PK);

    private Libralink.PaymentRequest paymentRequest = PaymentRequestBuilder.newBuilder()
            .addCorrelationId(UUID.randomUUID())
            .addAmount(BigDecimal.valueOf(100))
            .addCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            .addFrom(PAYER_CRED.getAddress())
            .addFromProc(PROCESSOR_CRED.getAddress())
            .addTo(PAYEE_CRED.getAddress())
            .addToProc(PROCESSOR_CRED.getAddress())
            .addCurrency("USDC")
            .addNote("")
        .build();

    private Libralink.Envelope unsignedPaymentRequestEnvelope = EnvelopeBuilder.newBuilder()
            .addId(UUID.randomUUID())
            .addContent(EnvelopeContentBuilder.newBuilder()
                    .addEntity(Any.pack(paymentRequest))
                    .build())
            .build();

    private Libralink.Envelope signedPaymentRequestEnvelope = SignatureHelper.sign(unsignedPaymentRequestEnvelope, PAYEE_CRED, Libralink.SignatureReason.CONFIRM);

    private Libralink.SurchargeRequest surchargeRequest = SurchargeRequestBuilder.newBuilder()
            .addAmount(BigDecimal.valueOf(5))
            .addEnvelope(signedPaymentRequestEnvelope)
            .addTo(INTERMEDIARY_CRED.getAddress())
            .addToProc(PROCESSOR_CRED.getAddress())
            .addNote("")
            .build();

    private Libralink.Envelope unsignedSurchargeRequestEnvelope = EnvelopeBuilder.newBuilder()
            .addId(UUID.randomUUID())
            .addContent(EnvelopeContentBuilder.newBuilder()
                    .addEntity(Any.pack(surchargeRequest))
                    .build())
            .build();

    private Libralink.Envelope signedSurchargeRequestEnvelope = SignatureHelper.sign(unsignedSurchargeRequestEnvelope, INTERMEDIARY_CRED, Libralink.SignatureReason.CONFIRM);

    private Libralink.Envelope payerSignedSurchargeRequestEnvelope = SignatureHelper.sign(
            EnvelopeBuilder.newBuilder()
                    .addId(UUID.randomUUID())
                    .addContent(EnvelopeContentBuilder.newBuilder()
                            .addEntity(Any.pack(signedSurchargeRequestEnvelope))
                            .build())
                .build(),
            PAYER_CRED, Libralink.SignatureReason.CONFIRM);

    private Libralink.ECheck eCheck = ECheckBuilder.newBuilder()
            .addCorrelationId(UUID.fromString(paymentRequest.getCorrelationId()))
            .addCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            .addExpiresAt(LocalDateTime.now().plusDays(5).toEpochSecond(ZoneOffset.UTC))
            .addCurrency(paymentRequest.getCurrency())
            .addNote("")
            .addFaceAmount(new BigDecimal(paymentRequest.getAmount()).add(new BigDecimal(surchargeRequest.getAmount())))
            .addTo(PAYEE_CRED.getAddress())
            .addToProc(PROCESSOR_CRED.getAddress())
            .addFrom(PAYER_CRED.getAddress())
            .addFromProc(PROCESSOR_CRED.getAddress())
            .addSplits(List.of(
                ECheckSplitBuilder.newBuilder()
                        .addAmount(new BigDecimal(paymentRequest.getAmount()))
                        .addTo(paymentRequest.getTo())
                        .addToProc(paymentRequest.getToProc())
                    .build(),
                ECheckSplitBuilder.newBuilder()
                        .addAmount(new BigDecimal(surchargeRequest.getAmount()))
                        .addTo(surchargeRequest.getTo())
                        .addToProc(surchargeRequest.getToProc())
                    .build()
            ))
            .build();

    private Libralink.Envelope payerSignedEnvelope = SignatureHelper.sign(
            EnvelopeBuilder.newBuilder()
                    .addContent(EnvelopeContentBuilder.newBuilder()
                            .addEntity(Any.pack(eCheck))
                            .build())
                    .addId(UUID.randomUUID())
                .build(),
            PAYER_CRED, Libralink.SignatureReason.CONFIRM);

    private Libralink.Envelope processorSignedEnvelope = SignatureHelper.sign(
            EnvelopeBuilder.newBuilder()
                    .addContent(EnvelopeContentBuilder.newBuilder()
                            .addEntity(Any.pack(payerSignedEnvelope))
                            .build())
                    .addId(UUID.randomUUID())
                    .build(),
            PROCESSOR_CRED, Libralink.SignatureReason.CONFIRM);

    public PaymentRequestSurchargeBuilderTest() throws Exception {
    }

    @Test
    public void wrap_payment_request_with_surcharge() throws Exception {
        LOG.info(JsonUtils.toJson(payerSignedSurchargeRequestEnvelope));
        LOG.info(JsonUtils.toJson(processorSignedEnvelope));
    }
}
