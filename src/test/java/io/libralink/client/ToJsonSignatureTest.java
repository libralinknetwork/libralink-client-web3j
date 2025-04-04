package io.libralink.client;

import io.libralink.client.payment.protocol.AbstractEntity;
import io.libralink.client.payment.protocol.echeck.DepositApproval;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.Signature;
import io.libralink.client.payment.util.JsonUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class ToJsonSignatureTest {

    @Test
    public void to_json_test() throws Exception {

        DepositApproval approval = DepositApproval.builder()
                .addCreatedAt(1743526954033L)
                .addCheckId(UUID.fromString("4f775a54-f028-4632-a3ba-10f85905b148"))
                .addPayer("0x12345")
                .addPayee("0x12346")
                .addAmount(BigDecimal.valueOf(100))
                .addId(UUID.fromString("8a29d3c8-4b8e-43dc-8542-9abd6231de7a"))
                .build();

        Signature signature = Signature.builder()
                .addPub("pubKey1")
                .addSig("signature1")
                .build();

        AbstractEntity.AbstractEntityBuilder envelopeBuilder = Envelope.builder()
                .addContent(approval)
                .addSignature(signature)
                .addId(UUID.fromString("c5e5fb06-f317-4acf-a1e2-d0cc6c7e0d07"));

        Envelope envelope = envelopeBuilder.build();
        String json = JsonUtils.toJson(envelope);
        System.out.println(json);

        Envelope deserilaized = JsonUtils.fromJson(json, Envelope.class);
        assertNotNull(deserilaized);
    }
}
