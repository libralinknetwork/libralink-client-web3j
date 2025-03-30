package io.libralink.client.payment;

import io.libralink.client.payment.protocol.Envelope;
import io.libralink.client.payment.protocol.body.BodyContent;
import io.libralink.client.payment.protocol.body.BodyEnvelope;
import io.libralink.client.payment.protocol.body.PaymentRequestBody;
import io.libralink.client.payment.protocol.header.*;
import io.libralink.client.payment.signature.SignatureHelper;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentRequestCreateUseCaseTest {

    /* Payer Credentials */
    private String PAYER_ADDR = "0x490f5932e1098ce96b0ac6770a722f9ca793aa64";
    private String PAYER_PK = "ad333a0a437df9c7388cc5ae092fe978fbc1a646d73196bf68320e25b7af67c";

    /* Payee Credentials */
    private String PAYEE_ADDR = "0x8f33dceeedfcf7185aa480ee16db9b9bb745756e";
    private String PAYEE_PK = "64496cc969654b231087af38ce654aa8d539fea0970d90366e42a5e39761f3f5";

    /* Trusted Party Credentials */
    private String TP_ADDR = "0x127cc4d943dff0a4bd6b024a96554a84e6247440";
    private String TP_PK = "1c9f40ff758f70b5c59c9df78738fdd122c4f5d6324e61448a3f516b7df00b22";

    @Test
    public void test_create_payment_request() throws Exception {

        /* 1. Payee creates Payment Request and signs it */
        Envelope paymentRequest = createPaymentRequest(PAYER_ADDR, PAYEE_ADDR, BigDecimal.valueOf(150));

        BodyEnvelope body = paymentRequest.getBody();
        String payeeSigValue = SignatureHelper.sign(body, Credentials.create(PAYEE_PK));
        Signature payeeSignature = Signature.builder()
            .addAddress(PAYEE_ADDR)
            .addNonce(UUID.randomUUID().toString())
            .addSig(payeeSigValue)
            .build();

        HeaderWithSignature payeeHeader = HeaderWithSignature.builder()
            .addBodySig(payeeSignature)
            .addContent(EmptyHeaderContent.builder().build())
            .build();

        paymentRequest = Envelope.builder(paymentRequest)
            .addHeader(HeaderEnvelope.builder(paymentRequest.getHeader())
                .addContent(payeeHeader).build()).build();

        assertTrue(SignatureHelper.verify(paymentRequest));

        /* 2. Payee sends request to Payer, Payer agrees with everything and signs the request */

        String payerSigValue = SignatureHelper.sign(body, Credentials.create(PAYER_PK));
        Signature payerSignature = Signature.builder()
                .addAddress(PAYER_ADDR)
                .addNonce(UUID.randomUUID().toString())
                .addSig(payerSigValue)
                .build();

        HeaderWithSignature payerHeader = HeaderWithSignature.builder()
                .addBodySig(payerSignature)
                .addContent(EmptyHeaderContent.builder().build())
                .build();

        paymentRequest = Envelope.builder(paymentRequest)
                .addHeader(HeaderEnvelope.builder(paymentRequest.getHeader())
                        .addContent(payerHeader).build()).build();

        assertTrue(SignatureHelper.verify(paymentRequest));

        /* 3. Payment is not guaranteed at this stage, hence Payer sends request to Trusted Party to sign & declare the fee size */

        FeeStructure feeStructure = FeeStructure.builder()
                .addFlatFee(BigDecimal.ONE)
                .addPercentFee(BigDecimal.TEN)
                .build();

        PartyHeaderContent partyHeaderContent = PartyHeaderContent.builder()
                .addFee(feeStructure)
                .build();

        String partyBodySigValue = SignatureHelper.sign(body, Credentials.create(TP_PK));
        String partyHeaderSigValue = SignatureHelper.sign(partyHeaderContent, Credentials.create(TP_PK));

        Signature partyBodySignature = Signature.builder()
                .addAddress(TP_ADDR)
                .addNonce(UUID.randomUUID().toString())
                .addSig(partyBodySigValue)
                .build();

        Signature partyHeaderSignature = Signature.builder()
                .addAddress(TP_ADDR)
                .addNonce(UUID.randomUUID().toString())
                .addSig(partyHeaderSigValue)
                .build();

        HeaderWithSignature partyHeader = HeaderWithSignature.builder()
                .addBodySig(partyBodySignature)
                .addHeaderSig(partyHeaderSignature)
                .addContent(partyHeaderContent)
                .build();

        paymentRequest = Envelope.builder(paymentRequest)
                .addHeader(HeaderEnvelope.builder(paymentRequest.getHeader())
                        .addContent(partyHeader).build()).build();

        assertTrue(SignatureHelper.verify(paymentRequest));
    }

    public Envelope createPaymentRequest(String payer, String payee, BigDecimal amount) {

        BodyContent bodyContent = PaymentRequestBody.builder()
                .addAmount(amount)
                .addCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                .addPayee(payee)
                .addPayeeParty(TP_ADDR)
                .addPayer(payer)
                .addPayerParty(TP_ADDR)
                .addType("USDT")
                .addNote("")
                .build();

        BodyEnvelope bodyEnvelope = BodyEnvelope.builder()
                .addBody(bodyContent).build();

        HeaderEnvelope headerEnvelope = HeaderEnvelope.builder()
                .addContent(new ArrayList<>()).build();

        return Envelope.builder()
                .addBody(bodyEnvelope)
                .addHeader(headerEnvelope)
                .addEnvelopeId(UUID.randomUUID())
                .build();
    }

    public void test_create_key_pair() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        Credentials credentials = Credentials.create(ecKeyPair);

        System.out.println(credentials.getAddress());
        System.out.println(credentials.getEcKeyPair().getPrivateKey().toString(16));
    }
}
