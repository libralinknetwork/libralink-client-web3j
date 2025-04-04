package io.libralink.client.payment.protocol.echeck;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DepositApprovalBuilderTest {

    private Map<String, Object> attributes = Map.of(
        "checkId", UUID.randomUUID(),
        "amount", BigDecimal.valueOf(100),
        "createdAt", 1743526954033L,
        "payer", "0x12345",
        "payee", "0x12346"
    );

    private void buildDepositApproval(Map<String, Object> attrs) throws BuilderException {
        DepositApproval.Builder builder = DepositApproval.builder();
        builder.addCheckId((UUID) attrs.get("checkId"));
        builder.addAmount((BigDecimal) attrs.get("amount"));
        builder.addCreatedAt((long) attrs.getOrDefault("createdAt", -1L));
        builder.addPayee((String) attrs.get("payee"));
        builder.addPayer((String) attrs.get("payer"));
        builder.build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_check_id() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("checkId");
        buildDepositApproval(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_amount() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("amount");
        buildDepositApproval(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payer() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("payer");
        buildDepositApproval(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_payee() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("payee");
        buildDepositApproval(attrs);
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_created_at() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        attrs.remove("createdAt");
        buildDepositApproval(attrs);
    }

    @Test
    public void no_error_missing_note() throws BuilderException {
        Map<String, Object> attrs = new HashMap<>(attributes);
        buildDepositApproval(attrs);
    }
}
