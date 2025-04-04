package io.libralink.client.payment.protocol;

import io.libralink.client.payment.protocol.exception.BuilderException;

public abstract class BaseEntity extends AbstractEntity {

    public abstract static class BaseEntityBuilder extends AbstractEntityBuilder {

        protected BaseEntityBuilder() {}

        public <T extends BaseEntity> T build(T entity) throws BuilderException {
            return super.build(entity);
        }
    }
}
