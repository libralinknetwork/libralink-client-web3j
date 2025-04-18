package io.libralink.client.payment.proto.builder.echeck;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ECheckBuilderTest {

    private Map<String, Object> attributes = Map.of(
            "currency", "USDC",
            "faceAmount", BigDecimal.valueOf(100),
            "from", "0x12345",
            "fromProc", "0x123451",
            "to", "0x12346",
            "toProc", "0x123461",
            "createdAt", 1743526954033L,
            "expiresAt", 1743526955000L,
            "correlationId", UUID.randomUUID()
    );

    private void buildECheck(Map<String, Object> attrs) throws BuilderException {
        ECheckBuilder.newBuilder()
            .addCorrelationId((UUID) attrs.get("correlationId"))
            .addCurrency((String) attrs.get("currency"))
            .addFaceAmount((BigDecimal) attrs.get("faceAmount"))
            .addFrom((String) attrs.get("from"))
            .addFromProc((String) attrs.get("fromProc"))
            .addTo((String) attrs.get("to"))
            .addToProc((String) attrs.get("toProc"))
            .addCreatedAt((long) attrs.getOrDefault("createdAt", -1L))
            .addExpiresAt((long) attrs.getOrDefault("expiresAt", -1L))
            .addSplits(List.of(ECheckSplitBuilder.newBuilder()
                .addTo((String) attrs.get("to"))
                .addToProc((String) attrs.get("toProc"))
                .addAmount((BigDecimal) attrs.get("faceAmount"))
                .build()))
            .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_expires_at() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("expiresAt");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_created_at() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("createdAt");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payee_processor() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("toProc");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payee() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("to");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payer_processor() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("fromProc");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payer() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("from");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_currency() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("currency");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_face_amount() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("faceAmount");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_expires_at_less_than_created_at() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.replace("expiresAt", 100L);
        buildECheck(attrs);
    }

    @Test
    public void no_error_missing_note() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        buildECheck(attrs);
    }
}
