package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.echeck.ECheck;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.signature.SignatureHelper;
import io.libralink.client.payment.util.EnvelopeUtils;
import io.libralink.client.payment.validator.EntityValidationRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ECheckSignedByPayerValidityRule implements EntityValidationRule {

    @Override
    public String name() {
        return "RULE_ECHECK_SIGNED_BY_PAYER";
    }

    private static final Logger LOG = LoggerFactory.getLogger(ECheckSignedByPayerValidityRule.class);

    @Override
    public boolean isValid(Envelope envelope) {

        Optional<String> payerOptional = EnvelopeUtils.extractEntityAttribute(envelope, ECheck.class, ECheck::getPayer);
        if (payerOptional.isEmpty()) {
            return Boolean.FALSE;
        }

        String payer = payerOptional.get();
        Optional<Envelope> payerEnvelopeOptional = EnvelopeUtils.findSignedEnvelopeByPub(envelope, payer);

        if (payerEnvelopeOptional.isEmpty()) {
            return Boolean.FALSE;
        }

        Envelope payerEnvelope = payerEnvelopeOptional.get();

        try {
            return SignatureHelper.verify(payerEnvelope);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return Boolean.FALSE;
    }
}
