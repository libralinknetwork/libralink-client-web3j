package io.libralink.client.payment.signature;

import io.libralink.client.payment.protocol.header.FeeStructure;
import io.libralink.client.payment.protocol.header.PartyHeaderContent;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SignatureHelperTest {

    final String PARTY_PK = "19dc73eee8a41b5ea3d361c9c3f5b57af96835b242fc223e45f2b79f9194d4f9";
    final Credentials partyCred = Credentials.create(PARTY_PK);
    final FeeStructure feeStructure = FeeStructure.builder()
            .addFlatFee(BigDecimal.ONE)
            .addPercentFee(BigDecimal.ZERO)
            .build();
    final PartyHeaderContent content = PartyHeaderContent.builder()
            .addFee(feeStructure)
            .build();

    @Test
    public void test_party_header_content_valid_signature() throws Exception {

        final String signature = SignatureHelper.sign(content, partyCred);
        assertNotNull(signature);

        boolean isValid = SignatureHelper.verify(content, partyCred.getAddress(), signature);
        assertTrue(isValid);
    }

    @Test
    public void test_party_header_content_invalid_signature() throws Exception {
        final String signature = SignatureHelper.sign(content, partyCred);
        assertNotNull(signature);

        final FeeStructure feeStructure2 = FeeStructure.builder()
                .addFlatFee(new BigDecimal("0.01"))
                .addPercentFee(new BigDecimal("0.02"))
                .build();

        boolean isValid = SignatureHelper.verify(PartyHeaderContent.builder().addFee(feeStructure2).build(),
                partyCred.getAddress(), signature);
        assertFalse(isValid);
    }
}
