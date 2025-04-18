package io.libralink.client.payment.validator;

import io.libralink.client.payment.proto.Libralink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class BaseEntityValidator {

    private static Logger LOG = LoggerFactory.getLogger(BaseEntityValidator.class);

    private BaseEntityValidator() {}

    public static Optional<String> findFirstFailedRule(Libralink.Envelope envelope, Class<? extends EntityValidationRule>... ruleClasses)
            throws InstantiationException, IllegalAccessException {

        for (Class<? extends EntityValidationRule> ruleClass: ruleClasses) {

            EntityValidationRule rule = ruleClass.newInstance();
            try {
                if (!rule.isValid(envelope)) {
                    return Optional.of(rule.name());
                }
            } catch (Exception e) {
                return Optional.of(rule.name());
            }
        }

        return Optional.empty();
    }
}
