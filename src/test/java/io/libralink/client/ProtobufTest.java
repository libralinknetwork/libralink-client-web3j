package io.libralink.client;

import com.google.protobuf.InvalidProtocolBufferException;
import io.libralink.client.payment.proto.Libralink;
import org.junit.Test;
import com.google.protobuf.util.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ProtobufTest {

    @Test
    public void test_protobuf_serialize() throws InvalidProtocolBufferException {

        Libralink.PaymentRequest request = Libralink.PaymentRequest.newBuilder()
            .setAmount("100")
            .setCorrelationId(UUID.randomUUID().toString())
            .setFrom("0x123")
            .setFromProc("0x1234")
            .setTo("0x234")
            .setToProc("0x2345")
            .setCurrency("USDC")
            .setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            .setNote("")
            .build();

        byte[] binaryData = request.toByteArray();
        String base64Encoded = Base64.getEncoder().encodeToString(binaryData);

        System.out.println("Protobuf (" + base64Encoded.length() + ") - " + base64Encoded);

        String jsonString = JsonFormat.printer().print(request);
        System.out.println("JSON (" + jsonString.length() + ") " + jsonString);

        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        Libralink.PaymentRequest parsed = Libralink.PaymentRequest.parseFrom(decodedBytes);
        assertEquals("0x123", parsed.getFrom());
    }
}
