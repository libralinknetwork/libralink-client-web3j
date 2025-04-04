package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.validator.EntityValidationRule;

public class DepositApprovalSignedByPayerValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_DEPOSIT_APPROVAL_SIGNED_BY_PAYER";
    }

    @Override
    public boolean isValid(Envelope envelope) {

        /* Signature */
        throw new RuntimeException("Not Implemented");
    }
}
