package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.validator.EntityValidationRule;

public class PaymentRequestSignedByPayerValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_PAYMENT_REQUEST_SIGNED_BY_PAYER";
    }

    @Override
    public boolean isValid(Libralink.Envelope envelope) {

        /* Signature */
        throw new RuntimeException("Not Implemented");
    }
}
