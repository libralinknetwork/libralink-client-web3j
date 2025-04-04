package io.libralink.client.payment.protocol.processing;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

import java.math.BigDecimal;

public class ProcessingFeeBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_fee_type() throws BuilderException {

        ProcessingFee.builder()
            .addAmount(BigDecimal.valueOf(100))
            .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_amount() throws BuilderException {

        ProcessingFee.builder()
            .addFeeType("flat")
            .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_all_attributes() throws BuilderException {

        ProcessingFee.builder()
            .build();
    }
}
