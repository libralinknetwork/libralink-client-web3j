package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.util.List;
import java.util.UUID;

public final class DepositResponseBuilder {

    private UUID checkEnvelopeId;
    private List<UUID> requestEnvelopeIds;

    private DepositResponseBuilder() {}

    public static DepositResponseBuilder newBuilder() {
        return new DepositResponseBuilder();
    }

    public DepositResponseBuilder addCheckEnvelopeId(UUID checkEnvelopeId) {
        this.checkEnvelopeId = checkEnvelopeId;
        return this;
    }

    public DepositResponseBuilder addRequestEnvelopeIds(List<UUID> requestEnvelopeIds) {
        this.requestEnvelopeIds = requestEnvelopeIds;
        return this;
    }

    public Libralink.DepositResponse build() throws BuilderException {

        if (checkEnvelopeId == null || requestEnvelopeIds == null || requestEnvelopeIds.isEmpty()) {
            throw new BuilderException();
        }

        Libralink.DepositResponse.Builder builder = Libralink.DepositResponse.newBuilder()
                .setCheckEnvelopeId(checkEnvelopeId.toString());

        for (int i=0; i<requestEnvelopeIds.size(); i++) {
            builder.addRequestEnvelopeIds(requestEnvelopeIds.get(i).toString());
        }

        return builder.build();
    }
}
