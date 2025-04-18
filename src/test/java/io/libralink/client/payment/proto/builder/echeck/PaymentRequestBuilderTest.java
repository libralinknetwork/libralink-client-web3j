package io.libralink.client.payment.proto.builder.echeck;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaymentRequestBuilderTest {

    private Map<String, Object> attributes = Map.of(
        "correlationId", UUID.randomUUID(),
        "amount", BigDecimal.valueOf(100),
        "createdAt", 1743526954033L,
        "from", "0x12345",
        "fromProc", "0x12345",
        "to", "0x12346",
        "toProc", "0x12346",
        "currency", "USDC"
    );

    private void buildPaymentRequest(Map<String, Object> attrs) throws BuilderException {
        PaymentRequestBuilder.newBuilder()
            .addCorrelationId((UUID) attrs.get("correlationId"))
            .addAmount((BigDecimal) attrs.get("amount"))
            .addCreatedAt((long) attrs.getOrDefault("createdAt", -1L))
            .addFrom((String) attrs.get("from"))
            .addFromProc((String) attrs.get("fromProc"))
            .addTo((String) attrs.get("to"))
            .addToProc((String) attrs.get("toProc"))
            .addCurrency((String) attrs.get("currency"))
        .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_correlation_id() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("correlationId");
        buildPaymentRequest(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_amount() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("amount");
        buildPaymentRequest(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_from() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("from");
        buildPaymentRequest(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_to() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("to");
        buildPaymentRequest(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_created_at() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("createdAt");
        buildPaymentRequest(attrs);
    }

    @Test
    public void no_error_missing_note() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        buildPaymentRequest(attrs);
    }
}
