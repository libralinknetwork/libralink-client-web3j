package io.libralink.client.payment.protocol.header;

public class Signature {

    private String address;
    private String salt;
    private String sig;

    private Signature() {}

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public String getSalt() {
        return salt;
    }

    void setSalt(String salt) {
        this.salt = salt;
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
        private String salt;
        private String sig;

        private Builder() {}

        public Builder addAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder addSalt(String salt) {
            this.salt = salt;
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
            signature.setSalt(salt);
            return signature;
        }
    }
}

