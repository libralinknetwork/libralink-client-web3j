package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.validator.EntityValidationRule;

public class DepositRequestAmountValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_DEPOSIT_REQUEST_AMOUNT_VALID";
    }

    @Override
    public boolean isValid(Envelope envelope) {

        /* SUM(Deposit Approvals) <= E-Check */
        throw new RuntimeException("Not Implemented");
    }
}
