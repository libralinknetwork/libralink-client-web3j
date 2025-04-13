package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.api.balance.GetBalanceRequest;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.signature.SignatureHelper;
import io.libralink.client.payment.util.EnvelopeUtils;
import io.libralink.client.payment.validator.EntityValidationRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GetBalanceRequestSignedRule implements EntityValidationRule {

    private final static Logger LOG = LoggerFactory.getLogger(GetBalanceRequestSignedRule.class);

    @Override
    public String name() {
        return "RULE_GET_BALANCE_REQUEST_SIGNED_VALID";
    }

    @Override
    public boolean isValid(final Envelope envelope) {

        Optional<String> addressOptional = EnvelopeUtils.extractEntityAttribute(
                envelope, GetBalanceRequest.class, GetBalanceRequest::getAddress);

        Optional<Envelope> envelopeOptional = addressOptional.flatMap(address ->
                EnvelopeUtils.findSignedEnvelopeByPub(envelope, address));

        if (envelopeOptional.isPresent()) {
            try {
                return SignatureHelper.verify(envelopeOptional.get());
            } catch (Exception ex) {
                LOG.warn(ex.getMessage());
            }
        }

        return Boolean.FALSE;
    }
}
