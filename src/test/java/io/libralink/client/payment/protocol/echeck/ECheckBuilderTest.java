package io.libralink.client.payment.protocol.echeck;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ECheckBuilderTest {
    
    private Map<String, Object> attributes = Map.of(
            "currency", "USDC",
            "faceAmount", BigDecimal.valueOf(100),
            "payer", "0x12345",
            "payerProcessor", "0x123451",
            "payee", "0x12346",
            "payeeProcessor", "0x123461",
            "createdAt", 1743526954033L,            
            "expiresAt", 1743526955000L            
    );

    private void buildECheck(Map<String, Object> attrs) throws BuilderException {
        ECheck.Builder builder = ECheck.builder();
        builder.addCurrency((String) attrs.get("currency"));
        builder.addFaceAmount((BigDecimal) attrs.get("faceAmount"));
        builder.addPayer((String) attrs.get("payer"));
        builder.addPayerProcessor((String) attrs.get("payerProcessor"));
        builder.addPayee((String) attrs.get("payee"));
        builder.addPayeeProcessor((String) attrs.get("payeeProcessor"));
        builder.addCreatedAt((long) attrs.getOrDefault("createdAt", -1L));
        builder.addExpiresAt((long) attrs.getOrDefault("expiresAt", -1L));
        builder.build();
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
        attrs.remove("payeeProcessor");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payee() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("payee");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payer_processor() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("payerProcessor");
        buildECheck(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payer() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("payer");
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
