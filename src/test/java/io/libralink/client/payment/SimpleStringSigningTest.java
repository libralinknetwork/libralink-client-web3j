package io.libralink.client.payment;

import io.libralink.client.payment.util.EncryptionUtils;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import static org.junit.Assert.assertEquals;

public class SimpleStringSigningTest {

    /* Payer Credentials */
    final private String PAYER_PK = "7af8df13f6aebcbd9edd369bb5f67bf7523517685491fea776bb547910ff5673";
    final private Credentials PAYER_CRED = Credentials.create(PAYER_PK);

    @Test
    public void test_simple_signing() throws Exception {

        String input = "Simple string";
        String signature = EncryptionUtils.sign(input, PAYER_CRED);

        assertEquals("0x2d29c1905e79a374b5d24cb9f662226da86a72941ea2ce6e14649b8d50d144bd22b05c14e8670d5ef49cc1b88dbf3a6fbcc1886c9a9923c06105c4f6ee48f2351c", signature);
    }
}
