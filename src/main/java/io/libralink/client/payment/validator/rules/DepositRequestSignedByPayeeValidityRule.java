package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.validator.EntityValidationRule;

public class DepositRequestSignedByPayeeValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_DEPOSIT_SIGNED_BY_PAYEE";
    }

    @Override
    public boolean isValid(Envelope envelope) {

        /* Validate Payee signature */

        throw new RuntimeException("Not Implemented");
    }
}
