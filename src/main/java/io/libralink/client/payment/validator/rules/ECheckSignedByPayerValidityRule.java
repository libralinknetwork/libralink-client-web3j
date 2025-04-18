package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.proto.Libralink;
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
    public boolean isValid(Libralink.Envelope envelope) throws Exception {

        Optional<String> payerOptional = EnvelopeUtils.extractEntityAttribute(envelope, Libralink.ECheck.class, Libralink.ECheck::getFrom);
        if (payerOptional.isEmpty()) {
            return Boolean.FALSE;
        }

        String payer = payerOptional.get();
        Optional<Libralink.Envelope> payerEnvelopeOptional = EnvelopeUtils.findSignedEnvelopeByPub(envelope, payer);

        if (payerEnvelopeOptional.isEmpty()) {
            return Boolean.FALSE;
        }

        Libralink.Envelope payerEnvelope = payerEnvelopeOptional.get();

        try {
            return SignatureHelper.verify(payerEnvelope);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return Boolean.FALSE;
    }
}
