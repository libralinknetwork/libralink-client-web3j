package io.libralink.client.payment.validator.rules;

import com.google.protobuf.InvalidProtocolBufferException;
import io.libralink.client.payment.proto.Libralink;
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
    public boolean isValid(Libralink.Envelope envelope) throws Exception {

        String payerProcessor = EnvelopeUtils.extractEntityAttribute(envelope, Libralink.ECheck.class, Libralink.ECheck::getFromProc).orElse(null);
        String payeeProcessor = EnvelopeUtils.extractEntityAttribute(envelope, Libralink.ECheck.class, Libralink.ECheck::getToProc).orElse(null);

        /* If Payer & Payee processors are different */
        if (payeeProcessor == null || !payeeProcessor.equals(payerProcessor)) {
            return Boolean.FALSE;
        }

        /* If single Processor envelope mentions intermediary */
        List<Libralink.Envelope> processorEnvelopes = EnvelopeUtils.findAllProcessingDetailsEnvelopes(envelope);
        Optional<Libralink.Envelope> nonEmptyIntermediaryEnvelopeOption = processorEnvelopes.stream().filter(env -> {
                try {
                    String intermediary = (env.getContent().getEntity().unpack(Libralink.ProcessingFee.class)).getIntermediary();
                    return intermediary != null && !intermediary.isEmpty();
                } catch (InvalidProtocolBufferException e) {
                    return false;
                }
            })
        .findFirst();

        return nonEmptyIntermediaryEnvelopeOption.isEmpty();
    }
}
