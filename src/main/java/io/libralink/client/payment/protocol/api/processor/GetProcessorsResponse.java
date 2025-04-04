package io.libralink.client.payment.protocol.api.processor;

import io.libralink.client.payment.protocol.api.APIObject;
import io.libralink.client.payment.protocol.exception.BuilderException;

import java.util.List;

public class GetProcessorsResponse extends APIObject {

    private List<ProcessorDetails> processors;

    public List<ProcessorDetails> getProcessors() {
        return processors;
    }

    void setProcessors(List<ProcessorDetails> processors) {
        this.processors = processors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder extends APIObjectBuilder {

        private List<ProcessorDetails> processors;

        private Builder() {}

        public Builder addProcessors(List<ProcessorDetails> processors) {
            this.processors = processors;
            return this;
        }

        public GetProcessorsResponse build() throws BuilderException {

            if (processors == null || processors.isEmpty()) {
                throw new BuilderException();
            }

            GetProcessorsResponse response = super.build(new GetProcessorsResponse());
            response.setProcessors(processors);
            return response;
        }
    }
}
