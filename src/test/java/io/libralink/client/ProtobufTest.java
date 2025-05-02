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
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ProtobufTest {

    /* Payer Credentials */
    final private String PAYER_PK = "7af8df13f6aebcbd9edd369bb5f67bf7523517685491fea776bb547910ff5673";
    final private Credentials PAYER_CRED = Credentials.create(PAYER_PK);

    /* PAYEE */
    final private String PAYEE_PK = "64496cc969654b231087af38ce654aa8d539fea0970d90366e42a5e39761f3f5";
    final private Credentials PAYEE_CRED = Credentials.create(PAYEE_PK);

    /* Processor Credentials */
    private String PROCESSOR_PK = "d601b629b288ce5ab659b4782e7f34cc2279ac729485302fdcc19d0fccb78b0d";
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
                    .addAlgorithm("SECP256K1")
                    .addAddress(PAYER_CRED.getAddress())
                    .addDeviceSharePayerDetails(sharePayerDetails)
                    .build())
                .build();

        byte[] binaryData = envelope.toByteArray();
        String base64Encoded = Base64.getEncoder().encodeToString(binaryData);

        System.out.println("SharePayerDetails - " + base64Encoded);
        String jsonString = JsonFormat.printer().print(envelope);
        System.out.println("SharePayerDetails\n" + jsonString);

        /* CiQ2ZGM0MTNlMC01NmRiLTQzZDQtYjJjZS1mYzYyZmRlZGQyMjMSW5oBWBIqMHhmMzk5MDJiMTMzZmJkY2Y5MjZjMWY0ODY2NWM5OGQxYjAyOGQ5MDVhGioweDEyN2NjNGQ5NDNkZmYwYTRiZDZiMDI0YTk2NTU0YTg0ZTYyNDc0NDA= */
    }

    @Test
    public void test_decode_payment_request() throws Exception {

        String base64Encoded = "ChZ1bmlxdWUtZW52ZWxvcGUtaWQtMTIzEvEBCgsweFRvQWRkcmVzcxoJc2VjcDI1NmsxIANK1AEKBjEzMC4wMBIqMHhmMzk5MDJiMTMzZmJkY2Y5MjZjMWY0ODY2NWM5OGQxYjAyOGQ5MDVhGioweDEyN2NjNGQ5NDNkZmYwYTRiZDZiMDI0YTk2NTU0YTg0ZTYyNDc0NDAiCzB4VG9BZGRyZXNzKioweDEyN2NjNGQ5NDNkZmYwYTRiZDZiMDI0YTk2NTU0YTg0ZTYyNDc0NDAyBFVTREM4qwFCCk9yZGVyIG5vdGVKJDI1ZGRkOGM4LTdhNDItNGI1YS04ZDA3LTA4MGJlOTlmMjg3MBoQc2lnbmVkLWJ5LWRldmljZQ==";

        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        Libralink.Envelope envelope = Libralink.Envelope.parseFrom(decodedBytes);
        Libralink.PaymentRequest paymentRequest = envelope.getContent().getPaymentRequest();

        assertEquals("signed-by-device", envelope.getSig());
        assertEquals("secp256k1", envelope.getContent().getAlgorithm());
//        assertEquals(PAYER_CRED.getAddress(), paymentRequest.getFrom());
//        assertEquals(PROCESSOR_CRED.getAddress(), paymentRequest.getFromProc());
//        System.out.println("Input Envelope\n" + JsonFormat.printer().print(envelope));

        /* Sign Payment Request by Payer */
        Libralink.Envelope payerEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(EnvelopeContentBuilder.newBuilder()
                        .addEnvelope(envelope)
                        .build())
                .build();

        Libralink.Envelope payerSignedEnvelope = SignatureHelper.sign(payerEnvelope, PAYER_CRED, Libralink.SignatureReason.CONFIRM);
        base64Encoded = Base64.getEncoder().encodeToString(payerSignedEnvelope.toByteArray());

        /* CiRiMWNhYmE3NS1hMmNhLTRmODEtODU0MC1kYTNmYzQ0MDVlZTQS2gIKKjB4ZjM5OTAyYjEzM2ZiZGNmOTI2YzFmNDg2NjVjOThkMWIwMjhkOTA1YRoJU0VDUDI1NksxIAMqngIKFnVuaXF1ZS1lbnZlbG9wZS1pZC0xMjMS8QEKCzB4VG9BZGRyZXNzGglzZWNwMjU2azEgA0rUAQoGMTMwLjAwEioweGYzOTkwMmIxMzNmYmRjZjkyNmMxZjQ4NjY1Yzk4ZDFiMDI4ZDkwNWEaKjB4MTI3Y2M0ZDk0M2RmZjBhNGJkNmIwMjRhOTY1NTRhODRlNjI0NzQ0MCILMHhUb0FkZHJlc3MqKjB4MTI3Y2M0ZDk0M2RmZjBhNGJkNmIwMjRhOTY1NTRhODRlNjI0NzQ0MDIEVVNEQzirAUIKT3JkZXIgbm90ZUokMjVkZGQ4YzgtN2E0Mi00YjVhLThkMDctMDgwYmU5OWYyODcwGhBzaWduZWQtYnktZGV2aWNlGoQBMHg1ZGVjMmNlNzAwOTA1MGY1YmMwNWEyOWE5OThmOTYxZmM3ZjNkMmZhYjgzNzE3YTMxN2YwYzkyNGM4YjMzMjZjNGM4ZGZkOWJlM2U2ZWJjODhiMGNkOGZjYzNkODdjOGNiOTA1MmMwZGY1MzhjNzljYmNhYmYzNjJlOGNhYTE4MjFi */
//        System.out.println("Envelope - " + base64Encoded);
//        System.out.println("Envelope\n" + JsonFormat.printer().print(payerSignedEnvelope));

        Libralink.DeviceTransactionCompleted completed = Libralink.DeviceTransactionCompleted.newBuilder()
                .setCorrelationId("eaead979-aa60-4679-90a7-2a8759ae2d38")
                .build();

        Libralink.Envelope completedEnvelope = EnvelopeBuilder.newBuilder()
                .addId(UUID.randomUUID())
                .addContent(Libralink.EnvelopeContent.newBuilder()
                        .setDeviceTransactionCompleted(completed)
                        .build())
                .build();

        base64Encoded = Base64.getEncoder().encodeToString(completedEnvelope.toByteArray());
        System.out.println("Envelope - " + base64Encoded);
        System.out.println("Envelope\n" + JsonFormat.printer().print(completedEnvelope));
    }
}
