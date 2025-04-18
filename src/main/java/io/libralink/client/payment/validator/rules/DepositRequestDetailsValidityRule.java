package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.validator.EntityValidationRule;

public class DepositRequestDetailsValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_DEPOSIT_REQUEST_DETAILS_VALID";
    }

    @Override
    public boolean isValid(Libralink.Envelope envelope) {

        /* Validate presence of E-Check & Deposit Approvals */

        /* Apply E-Check Validity Rule */

        /* Apply Deposit Approval(s) Validity Rule */

        throw new RuntimeException("Not Implemented");
    }
}
