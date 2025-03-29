package io.libralink.client.payment.protocol.header;

public class HeaderWithSignature {

    private HeaderContent content;
    private Signature bodySig;
    private Signature headerSig;

    private HeaderWithSignature() {}

    public HeaderContent getContent() {
        return content;
    }

    void setContent(HeaderContent content) {
        this.content = content;
    }

    public Signature getBodySig() {
        return bodySig;
    }

    void setBodySig(Signature bodySig) {
        this.bodySig = bodySig;
    }

    public Signature getHeaderSig() {
        return headerSig;
    }

    void setHeaderSig(Signature headerSig) {
        this.headerSig = headerSig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private HeaderContent content;
        private Signature bodySig;
        private Signature headerSig;

        private Builder() {}

        public Builder addHeaderSig(Signature headerSig) {
            this.headerSig = headerSig;
            return this;
        }

        public Builder addBodySig(Signature bodySig) {
            this.bodySig = bodySig;
            return this;
        }

        public Builder addContent(HeaderContent content) {
            this.content = content;
            return this;
        }

        public HeaderWithSignature build() {
            HeaderWithSignature headerWithSignature = new HeaderWithSignature();
            headerWithSignature.setContent(content);
            headerWithSignature.setBodySig(bodySig);
            headerWithSignature.setHeaderSig(headerSig);
            return headerWithSignature;
        }
    }
}

