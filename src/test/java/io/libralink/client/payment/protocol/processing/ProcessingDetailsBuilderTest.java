package io.libralink.client.payment.protocol.processing;

import io.libralink.client.payment.protocol.exception.BuilderException;
import org.junit.Test;

public class ProcessingDetailsBuilderTest {

    @Test(expected = BuilderException.class)
    public void throw_error_missing_attributes() throws BuilderException {

        ProcessingDetails.builder()
                .build();
    }
}
