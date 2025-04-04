package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.validator.EntityValidationRule;

public class ECheckSignedByOnePartyValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_ECHECK_SIGNED_BY_ONE_PARTY";
    }

    @Override
    public boolean isValid(Envelope envelope) {

        throw new RuntimeException("Not Implemented");
    }
}
