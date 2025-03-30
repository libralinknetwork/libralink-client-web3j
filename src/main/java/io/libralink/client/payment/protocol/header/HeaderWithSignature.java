package io.libralink.client.payment.protocol.header;

public class HeaderWithSignature {

    private HeaderContent header;
    private Signature bodySig;
    private Signature headerSig;

    private HeaderWithSignature() {}

    public HeaderContent getHeader() {
        return header;
    }

    void setHeader(HeaderContent header) {
        this.header = header;
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

        private HeaderContent header;
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

        public Builder addHeader(HeaderContent header) {
            this.header = header;
            return this;
        }

        public HeaderWithSignature build() {
            HeaderWithSignature headerWithSignature = new HeaderWithSignature();
            headerWithSignature.setHeader(header);
            headerWithSignature.setBodySig(bodySig);
            headerWithSignature.setHeaderSig(headerSig);
            return headerWithSignature;
        }
    }
}

