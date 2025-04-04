package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.validator.EntityValidationRule;

public class ECheckDetailsValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_ECHECK_DETAILS_VALID";
    }

    @Override
    public boolean isValid(Envelope envelope) {

        throw new RuntimeException("Not Implemented");
    }
}
