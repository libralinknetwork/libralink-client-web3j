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
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentRequestCreateUseCaseTest {

    /* Payer Credentials */
    private String PAYER_ADDR = "0xf39902b133fbdcf926c1f48665c98d1b028d905a";
    private String PAYER_PK = "7af8df13f6aebcbd9edd369bb5f67bf7523517685491fea776bb547910ff5673";

    /* Payee Credentials */
    private String PAYEE_ADDR = "0x8f33dceeedfcf7185aa480ee16db9b9bb745756e";
    private String PAYEE_PK = "64496cc969654b231087af38ce654aa8d539fea0970d90366e42a5e39761f3f5";

    /* Trusted Party Credentials */
    private String TP_ADDR = "0x127cc4d943dff0a4bd6b024a96554a84e6247440";
    private String TP_PK = "1c9f40ff758f70b5c59c9df78738fdd122c4f5d6324e61448a3f516b7df00b22";

    /* Intermediary Credentials */
    private String INTER_ADDR = "0x6ab62b06d9b20e7cbc163dc0bd823d1c6e053314";
    private String INTER_PK = "d550ba83c69c081ff689fc19bef6aebf4b8d6f39d59bfede59ed223d1f934d90";

    @Test
    public void test_create_payment_request() throws Exception {

        /* 1. Payee creates Payment Request and signs it */
        Envelope paymentRequest = createPaymentRequest(PAYER_ADDR, PAYEE_ADDR, BigDecimal.valueOf(150));

        BodyEnvelope body = paymentRequest.getBody();
        String payeeSigValue = SignatureHelper.sign(body, Credentials.create(PAYEE_PK));
        Signature payeeSignature = Signature.builder()
            .addAddress(PAYEE_ADDR)
            .addSalt("9f819ddd-f710-4b1d-904d-b119e01acf9f")
            .addSig(payeeSigValue)
            .build();

        HeaderWithSignature payeeHeader = HeaderWithSignature.builder()
            .addBodySig(payeeSignature)
            .addHeader(EmptyHeaderContent.builder().build())
            .build();

        paymentRequest = Envelope.builder(paymentRequest)
            .addHeader(HeaderEnvelope.builder(paymentRequest.getHeader())
                .addHeader(payeeHeader).build()).build();

        assertTrue(SignatureHelper.verify(paymentRequest));

        /* 2. Payee sends request to Payer, Payer agrees with everything and signs the request */

        String payerSigValue = SignatureHelper.sign(body, Credentials.create(PAYER_PK));
        Signature payerSignature = Signature.builder()
                .addAddress(PAYER_ADDR)
                .addSalt("607cadad-af8c-4579-bbf6-554026e5c4a7")
                .addSig(payerSigValue)
                .build();

        HeaderWithSignature payerHeader = HeaderWithSignature.builder()
                .addBodySig(payerSignature)
                .addHeader(EmptyHeaderContent.builder().build())
                .build();

        paymentRequest = Envelope.builder(paymentRequest)
                .addHeader(HeaderEnvelope.builder(paymentRequest.getHeader())
                        .addHeader(payerHeader).build()).build();

        assertTrue(SignatureHelper.verify(paymentRequest));

        /* 3. Payment is not guaranteed at this stage, hence Payer sends request to Trusted Party to sign & declare the fee size */

        FeeStructure feeStructure = FeeStructure.builder()
                .addFee(BigDecimal.TEN)
                .build();

        ProcessorHeaderContent partyHeaderContent = ProcessorHeaderContent.builder()
                .addFee(feeStructure)
                .addIntermediary(INTER_ADDR)
                .build();

        String partyBodySigValue = SignatureHelper.sign(body, Credentials.create(TP_PK));
        String partyHeaderSigValue = SignatureHelper.sign(partyHeaderContent, Credentials.create(TP_PK));

        Signature partyBodySignature = Signature.builder()
                .addAddress(TP_ADDR)
                .addSalt("6b693293-fd61-4de8-9b18-98e4aadadedd")
                .addSig(partyBodySigValue)
                .build();

        Signature partyHeaderSignature = Signature.builder()
                .addAddress(TP_ADDR)
                .addSalt("7ae2b744-b4eb-4e1a-adc4-59d47ae6d1b1")
                .addSig(partyHeaderSigValue)
                .build();

        HeaderWithSignature partyHeader = HeaderWithSignature.builder()
                .addBodySig(partyBodySignature)
                .addHeaderSig(partyHeaderSignature)
                .addHeader(partyHeaderContent)
                .build();

        paymentRequest = Envelope.builder(paymentRequest)
                .addHeader(HeaderEnvelope.builder(paymentRequest.getHeader())
                        .addHeader(partyHeader).build()).build();

        assertTrue(SignatureHelper.verify(paymentRequest));
    }

    public Envelope createPaymentRequest(String payer, String payee, BigDecimal amount) {

        BodyContent bodyContent = PaymentRequestBody.builder()
                .addAmount(amount)
                .addCreatedAt(1743526954033L)
                .addPayee(payee)
                .addPayeeProcessor(TP_ADDR)
                .addPayer(payer)
                .addPayerProcessor(TP_ADDR)
                .addType("USDT")
                .addNote("")
                .build();

        BodyEnvelope bodyEnvelope = BodyEnvelope.builder()
                .addBody(bodyContent).build();

        HeaderEnvelope headerEnvelope = HeaderEnvelope.builder()
                .addHeaders(new ArrayList<>()).build();

        return Envelope.builder()
                .addBody(bodyEnvelope)
                .addHeader(headerEnvelope)
                .addEnvelopeId(UUID.fromString("cdcaa5d4-bb25-4146-8c78-b736559443a1"))
                .build();
    }

    public void test_create_key_pair() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        Credentials credentials = Credentials.create(ecKeyPair);

        System.out.println(credentials.getAddress());
        System.out.println(credentials.getEcKeyPair().getPrivateKey().toString(16));
    }
}
