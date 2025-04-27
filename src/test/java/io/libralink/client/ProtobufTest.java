package io.libralink.client;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.device.DeviceSharePayerDetailsBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeBuilder;
import io.libralink.client.payment.proto.builder.envelope.EnvelopeContentBuilder;
import io.libralink.client.payment.signature.SignatureHelper;
import org.junit.Test;
import com.google.protobuf.util.JsonFormat;
import org.web3j.crypto.Credentials;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ProtobufTest {

    final private String PAYER_PK = "7af8df13f6aebcbd9edd369bb5f67bf7523517685491fea776bb547910ff5673";
    final private Credentials PAYER_CRED = Credentials.create(PAYER_PK);

    private String PROCESSOR_PK = "1c9f40ff758f70b5c59c9df78738fdd122c4f5d6324e61448a3f516b7df00b22";
    final private Credentials PROCESSOR_CRED = Credentials.create(PROCESSOR_PK);

    @Test
    public void test_create_share_payer_details() throws Exception {

        Libralink.DeviceSharePayerDetails sharePayerDetails = DeviceSharePayerDetailsBuilder.newBuilder()
                .addFrom(PAYER_CRED.getAddress())
                .addFromProc(PROCESSOR_CRED.getAddress())
                .addChallenge("")
                .build();

        Libralink.Envelope envelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(EnvelopeContentBuilder.newBuilder()
                    .addDeviceSharePayerDetails(sharePayerDetails)
                    .build())
                .build();

        byte[] binaryData = envelope.toByteArray();
        String base64Encoded = Base64.getEncoder().encodeToString(binaryData);

        System.out.println("SharePayerDetails - " + base64Encoded);
        String jsonString = JsonFormat.printer().print(envelope);
        System.out.println("SharePayerDetails\n" + jsonString);

        /* CiRkODAyZTM2MC1jMzQ4LTRjYWQtYjYwNS1mYjM1ODVmZWU4MTgSW5oBWBIqMHhmMzk5MDJiMTMzZmJkY2Y5MjZjMWY0ODY2NWM5OGQxYjAyOGQ5MDVhGioweDEyN2NjNGQ5NDNkZmYwYTRiZDZiMDI0YTk2NTU0YTg0ZTYyNDc0NDA= */
    }

    @Test
    public void test_decode_payment_request() throws Exception {

        String base64Encoded = "ChZ1bmlxdWUtZW52ZWxvcGUtaWQtMTIzEvEBCgsweFRvQWRkcmVzcxoJc2VjcDI1NmsxIANK1AEKBjEwMC4wMBIqMHhmMzk5MDJiMTMzZmJkY2Y5MjZjMWY0ODY2NWM5OGQxYjAyOGQ5MDVhGioweDEyN2NjNGQ5NDNkZmYwYTRiZDZiMDI0YTk2NTU0YTg0ZTYyNDc0NDAiCzB4VG9BZGRyZXNzKioweDEyN2NjNGQ5NDNkZmYwYTRiZDZiMDI0YTk2NTU0YTg0ZTYyNDc0NDAyBFVTREM4xgFCCk9yZGVyIG5vdGVKJDI1ZGRkOGM4LTdhNDItNGI1YS04ZDA3LTA4MGJlOTlmMjg3NRoQc2lnbmVkLWJ5LWRldmljZQ==";

        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        Libralink.Envelope envelope = Libralink.Envelope.parseFrom(decodedBytes);
        Libralink.PaymentRequest paymentRequest = envelope.getContent().getPaymentRequest();

        assertEquals("signed-by-device", envelope.getSig());
        assertEquals("secp256k1", envelope.getContent().getAlgorithm());
        assertEquals(PAYER_CRED.getAddress(), paymentRequest.getFrom());
        assertEquals(PROCESSOR_CRED.getAddress(), paymentRequest.getFromProc());

        /* Sign Payment Request by Payer */
        Libralink.Envelope payerEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(EnvelopeContentBuilder.newBuilder()
                        .addEnvelope(envelope)
                        .build())
                .build();

        Libralink.Envelope payerSignedEnvelope = SignatureHelper.sign(payerEnvelope, PAYER_CRED, Libralink.SignatureReason.CONFIRM);
        base64Encoded = Base64.getEncoder().encodeToString(payerSignedEnvelope.toByteArray());

        System.out.println("Envelope - " + base64Encoded);
        System.out.println("Envelope\n" + JsonFormat.printer().print(payerSignedEnvelope));
    }
}
