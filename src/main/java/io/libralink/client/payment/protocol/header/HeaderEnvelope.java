package io.libralink.client.payment.protocol.header;

import java.util.ArrayList;
import java.util.List;

public class HeaderEnvelope {

    private List<HeaderWithSignature> headers;

    private HeaderEnvelope() {}

    public List<HeaderWithSignature> getHeaders() {
        return headers;
    }

    void setHeaders(List<HeaderWithSignature> headers) {
        this.headers = headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(HeaderEnvelope headerEnvelope) {
        return new Builder(headerEnvelope.getHeaders());
    }

    public final static class Builder {

        private List<HeaderWithSignature> headers = new ArrayList<>();

        private Builder() {}

        private Builder(List<HeaderWithSignature> headers) {
            this.headers = headers;
        }

        public Builder addContent(List<HeaderWithSignature> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addContent(HeaderWithSignature item) {
            this.headers.add(item);
            return this;
        }

        public HeaderEnvelope build() {
            HeaderEnvelope headerEnvelope = new HeaderEnvelope();
            headerEnvelope.setHeaders(headers);
            return headerEnvelope;
        }
    }
}
