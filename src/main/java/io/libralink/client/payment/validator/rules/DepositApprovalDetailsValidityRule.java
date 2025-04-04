package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.validator.EntityValidationRule;

public class DepositApprovalDetailsValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_DEPOSIT_APPROVAL_DETAILS_VALID";
    }

    @Override
    public boolean isValid(Envelope envelope) {

        /* Validation, like Payment can't be done to himself */
        throw new RuntimeException("Not Implemented");
    }
}
