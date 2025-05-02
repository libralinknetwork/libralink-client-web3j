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

        String input = "Hello";
        String signature = EncryptionUtils.sign(input.getBytes(), PAYER_CRED);

        assertEquals("0x9c608fcebdea143b83faa315cd4ca4da0e9884076912b31905de32b638f12b0a5e65c06d314cb4250eaf0b3630a26a39bdbcad09a9830db3da8c70b7af48f4031c", signature);
    }
}
