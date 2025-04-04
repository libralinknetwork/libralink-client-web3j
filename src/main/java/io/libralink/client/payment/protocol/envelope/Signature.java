package io.libralink.client.payment.protocol.envelope;

import io.libralink.client.payment.protocol.exception.BuilderException;

public class Signature {

    private String pub;
    private String sig;

    public String getPub() {
        return pub;
    }

    void setPub(String pub) {
        this.pub = pub;
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

        private String pub;
        private String sig;

        private Builder() {}

        public Builder addPub(String pub) {
            this.pub = pub;
            return this;
        }

        public Builder addSig(String sig) {
            this.sig = sig;
            return this;
        }

        public Signature build() throws BuilderException {

            if (pub == null || sig == null) {
                throw new BuilderException();
            }

            Signature signature = new Signature();
            signature.setPub(pub);
            signature.setSig(sig);
            return signature;
        }
    }
}
