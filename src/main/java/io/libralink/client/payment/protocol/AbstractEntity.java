package io.libralink.client.payment.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.libralink.client.payment.protocol.api.account.RegisterKeyRequest;
import io.libralink.client.payment.protocol.api.account.RegisterKeyResponse;
import io.libralink.client.payment.protocol.api.balance.GetBalanceRequest;
import io.libralink.client.payment.protocol.api.balance.GetBalanceResponse;
import io.libralink.client.payment.protocol.api.echeck.DepositRequest;
import io.libralink.client.payment.protocol.api.echeck.DepositResponse;
import io.libralink.client.payment.protocol.api.error.ErrorResponse;
import io.libralink.client.payment.protocol.api.processor.GetProcessorsRequest;
import io.libralink.client.payment.protocol.api.processor.GetProcessorsResponse;
import io.libralink.client.payment.protocol.echeck.DepositApproval;
import io.libralink.client.payment.protocol.echeck.ECheck;
import io.libralink.client.payment.protocol.envelope.Envelope;
import io.libralink.client.payment.protocol.envelope.Signature;
import io.libralink.client.payment.protocol.exception.BuilderException;
import io.libralink.client.payment.protocol.processing.ProcessingDetails;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegisterKeyRequest.class, name = "RegisterKeyRequest"),
        @JsonSubTypes.Type(value = RegisterKeyResponse.class, name = "RegisterKeyResponse"),
        @JsonSubTypes.Type(value = GetBalanceRequest.class, name = "GetBalanceRequest"),
        @JsonSubTypes.Type(value = GetBalanceResponse.class, name = "GetBalanceResponse"),
        @JsonSubTypes.Type(value = DepositRequest.class, name = "DepositRequest"),
        @JsonSubTypes.Type(value = DepositResponse.class, name = "DepositResponse"),
        @JsonSubTypes.Type(value = ErrorResponse.class, name = "ErrorResponse"),
        @JsonSubTypes.Type(value = GetProcessorsRequest.class, name = "GetProcessorsRequest"),
        @JsonSubTypes.Type(value = GetProcessorsResponse.class, name = "GetProcessorsResponse"),
        @JsonSubTypes.Type(value = DepositApproval.class, name = "DepositApproval"),
        @JsonSubTypes.Type(value = ECheck.class, name = "ECheck"),
        @JsonSubTypes.Type(value = ProcessingDetails.class, name = "ProcessingDetails"),
        @JsonSubTypes.Type(value = Envelope.class, name = "Envelope"),
        @JsonSubTypes.Type(value = Signature.class, name = "Signature")
})
public abstract class AbstractEntity {

    private UUID id;

    public UUID getId() {
        return id;
    }

    void setId(UUID id) {
        this.id = id;
    }

    public abstract static class AbstractEntityBuilder {

        private UUID id;

        protected AbstractEntityBuilder() {}

        public AbstractEntityBuilder addId(UUID id) {
            this.id = id;
            return this;
        }

        public abstract <T extends AbstractEntity> T build() throws BuilderException;

        protected <T extends AbstractEntity> T build(T entity) throws BuilderException {
            entity.setId(id != null ? id: UUID.randomUUID());
            return entity;
        }
    }
}
