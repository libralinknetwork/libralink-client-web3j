package io.libralink.client.payment.proto.builder.fee;

import io.libralink.client.payment.proto.builder.exception.BuilderException;
import org.junit.Test;

import java.math.BigDecimal;

public class ProcessingFeeBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_fee_type() throws BuilderException {
        ProcessingFeeBuilder.newBuilder()
            .addAmount(BigDecimal.valueOf(100))
            .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_amount() throws BuilderException {

        ProcessingFeeBuilder.newBuilder()
            .addFeeType("flat")
            .build();
    }

    @Test(expected = BuilderException.class)
    public void throw_error_missing_all_attributes() throws BuilderException {

        ProcessingFeeBuilder.newBuilder()
            .build();
    }
}
