package io.libralink.client.payment.error;

import io.libralink.client.payment.protocol.error.ErrorEnvelope;
import io.libralink.client.payment.protocol.error.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorEnvelopeBuilderTest {

    @Test
    public void test_build_error_envelope() {

        ErrorMessage errorMessage = ErrorMessage.builder()
            .addCode(5)
            .addMessage("Error Code 5")
            .build();

        assertNotNull(errorMessage);
        assertEquals(5, errorMessage.getCode());
        assertEquals("Error Code 5", errorMessage.getMessage());

        ErrorEnvelope errorEnvelope = ErrorEnvelope.builder()
            .addContent(errorMessage)
            .build();

        assertNotNull(errorEnvelope);
        assertSame(errorMessage, errorEnvelope.getContent());
    }
}
