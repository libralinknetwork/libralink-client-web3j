package io.libralink.client.payment.protocol.api;

import io.libralink.client.payment.protocol.BaseEntity;
import io.libralink.client.payment.protocol.exception.BuilderException;

public abstract class APIObject extends BaseEntity {

    public abstract static class APIObjectBuilder extends BaseEntityBuilder {

        protected APIObjectBuilder() {}

        public <T extends APIObject> T build(T entity) throws BuilderException {
            return super.build(entity);
        }
    }
}
