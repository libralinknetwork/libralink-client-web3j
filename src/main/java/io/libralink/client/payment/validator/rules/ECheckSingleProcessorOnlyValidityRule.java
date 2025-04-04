package io.libralink.client.payment.validator.rules;

import io.libralink.client.payment.protocol.echeck.ECheck;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;
import io.libralink.client.payment.util.EnvelopeUtils;
import io.libralink.client.payment.validator.EntityValidationRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ECheckSingleProcessorOnlyValidityRule implements EntityValidationRule {

    private static final Logger LOG = LoggerFactory.getLogger(ECheckSingleProcessorOnlyValidityRule.class);

    @Override
    public String name() {
        return "RULE_ECHECK_SINGLE_PROCESSOR_ONLY";
    }

    @Override
    public boolean isValid(Envelope envelope) {

        String payerProcessor = EnvelopeUtils.extractEntityAttribute(envelope, ECheck.class, ECheck::getPayerProcessor).orElse(null);
        String payeeProcessor = EnvelopeUtils.extractEntityAttribute(envelope, ECheck.class, ECheck::getPayeeProcessor).orElse(null);

        /* If Payer & Payee processors are different */
        if (payeeProcessor == null || !payeeProcessor.equals(payerProcessor)) {
            return Boolean.FALSE;
        }

        /* If single Processor envelope mentions intermediary */
        List<Envelope> processorEnvelopes = EnvelopeUtils.findAllProcessingDetailsEnvelopes(envelope);
        Optional<Envelope> nonEmptyIntermediaryEnvelopeOption = processorEnvelopes.stream().filter(env -> ((ProcessingDetails) env.getContent()).getIntermediary() != null)
            .findFirst();

        return nonEmptyIntermediaryEnvelopeOption.isEmpty();
    }
}
