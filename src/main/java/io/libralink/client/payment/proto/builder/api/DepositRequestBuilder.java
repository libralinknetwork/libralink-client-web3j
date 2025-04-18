package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.util.List;

public final class DepositRequestBuilder {

    private Libralink.Envelope checkEnvelope;
    private List<Libralink.Envelope> requestEnvelopes;

    private DepositRequestBuilder() {}

    public static DepositRequestBuilder newBuilder() {
        return new DepositRequestBuilder();
    }

    public DepositRequestBuilder addCheckEnvelope(Libralink.Envelope checkEnvelope) {
        this.checkEnvelope = checkEnvelope;
        return this;
    }

    public DepositRequestBuilder addRequestEnvelopes(List<Libralink.Envelope> requestEnvelopes) {
        this.requestEnvelopes = requestEnvelopes;
        return this;
    }

    public Libralink.DepositRequest build() throws BuilderException {

        if (checkEnvelope == null || requestEnvelopes == null || requestEnvelopes.isEmpty()) {
            throw new BuilderException();
        }

        Libralink.DepositRequest.Builder builder = Libralink.DepositRequest.newBuilder()
                .setCheckEnvelope(checkEnvelope);

        for (int i=0; i<requestEnvelopes.size(); i++) {
            builder.addRequestEnvelopes(requestEnvelopes.get(i));
        }

        return builder.build();
    }
}
