package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.proto.Libralink;
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
    public boolean isValid(final Libralink.Envelope envelope) throws Exception {

        Optional<String> addressOptional = EnvelopeUtils.extractEntityAttribute(
                envelope, Libralink.GetBalanceRequest.class, Libralink.GetBalanceRequest::getAddress);

        Optional<Libralink.Envelope> envelopeOptional = addressOptional.flatMap(address ->
        {
            try {
                return EnvelopeUtils.findSignedEnvelopeByPub(envelope, address);
            } catch (Exception e) {
                return Optional.empty();
            }
        });

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
