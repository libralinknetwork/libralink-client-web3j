package io.libralink.client.payment.protocol.header;

import java.util.ArrayList;
import java.util.List;

public class HeaderEnvelope {

    private List<HeaderWithSignature> content;

    private HeaderEnvelope() {}

    public List<HeaderWithSignature> getContent() {
        return content;
    }

    void setContent(List<HeaderWithSignature> content) {
        this.content = content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(HeaderEnvelope headerEnvelope) {
        return new Builder(headerEnvelope.getContent());
    }

    public final static class Builder {

        private List<HeaderWithSignature> content = new ArrayList<>();

        private Builder() {}

        private Builder(List<HeaderWithSignature> content) {
            this.content = content;
        }

        public Builder addContent(List<HeaderWithSignature> content) {
            this.content = content;
            return this;
        }

        public Builder addContent(HeaderWithSignature item) {
            this.content.add(item);
            return this;
        }

        public HeaderEnvelope build() {
            HeaderEnvelope headerEnvelope = new HeaderEnvelope();
            headerEnvelope.setContent(content);
            return headerEnvelope;
        }
    }
}
