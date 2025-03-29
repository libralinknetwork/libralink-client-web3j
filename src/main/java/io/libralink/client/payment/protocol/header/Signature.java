package io.libralink.client.payment.protocol.header;

public class Signature {

    private String address;
    private String nonce;
    private String sig;

    private Signature() {}

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public String getNonce() {
        return nonce;
    }

    void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSig() {
        return sig;
    }

    void setSig(String sig) {
        this.sig = sig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private String address;
        private String nonce;
        private String sig;

        private Builder() {}

        public Builder addAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder addNonce(String nonce) {
            this.nonce = nonce;
            return this;
        }

        public Builder addSig(String sig) {
            this.sig = sig;
            return this;
        }

        public Signature build() {
            Signature signature = new Signature();
            signature.setAddress(address);
            signature.setSig(sig);
            signature.setNonce(nonce);
            return signature;
        }
    }
}

