package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.validator.EntityValidationRule;

/**
 * <b>Validation Rule</b>
 * Deposit Request valid from Processor standpoint, must have next
 * - Envelope signed by Payee, as only Payee is allowed to do E-Check deposit (request can be processed just once by the Processor)
 */
public class DepositRequestValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_DEPOSIT_REQUEST_VALID";
    }

    @Override
    public boolean isValid(Libralink.Envelope envelope) {

        /* Validate Payee signature */

        /* Validate presence of E-Check & Deposit Approvals */

        /* Apply E-Check Validity Rule */

        /* Apply Deposit Approval(s) Validity Rule */

        /* SUM(Deposit Approvals) <= E-Check */

        throw new RuntimeException("Not Implemented");
    }
}
