package io.libralink.client.payment.validator;

import io.libralink.client.payment.proto.Libralink;

public interface EntityValidationRule {

    String name();

    boolean isValid(Libralink.Envelope envelope) throws Exception;
}
