package io.libralink.client.payment.proto.builder.api;

import io.libralink.client.payment.proto.Libralink;
import io.libralink.client.payment.proto.builder.exception.BuilderException;

import java.util.List;

public final class GetProcessorResponseBuilder {

    private List<Libralink.ProcessorDetails> processors;

    private GetProcessorResponseBuilder() {}

    public static GetProcessorResponseBuilder newBuilder() {
        return new GetProcessorResponseBuilder();
    }

    public GetProcessorResponseBuilder addProcessors(List<Libralink.ProcessorDetails> processors) {
        this.processors = processors;
        return this;
    }

    public Libralink.GetProcessorsResponse build() throws BuilderException {

        if (processors == null || processors.isEmpty()) {
            throw new BuilderException();
        }

        Libralink.GetProcessorsResponse.Builder builder = Libralink.GetProcessorsResponse.newBuilder();

        for (int i=0; i< processors.size(); i++) {
            builder.addProcessors(processors.get(i));
        }

        return builder.build();
    }
}
