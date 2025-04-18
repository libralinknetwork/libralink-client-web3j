package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.validator.EntityValidationRule;

public class PaymentRequesyDetailsValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_PAYMENT_REQUEST_DETAILS_VALID";
    }

    @Override
    public boolean isValid(Libralink.Envelope envelope) {

        /* Validation, like Payment can't be done to himself */
        throw new RuntimeException("Not Implemented");
    }
}
