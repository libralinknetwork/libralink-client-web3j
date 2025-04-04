package io.libralink.client.payment.validator;

import io.libralink.client.payment.protocol.envelope.Envelope;

public interface EntityValidationRule {

    String name();

    boolean isValid(Envelope envelope);
}
