package io.libralink.client.payment.protocol.header;

public class PayerHeaderContent implements HeaderContent {

    private final String objectType = PayerHeaderContent.class.getSimpleName();

    private PayerHeaderContent() {}

    public String getObjectType() {
        return objectType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private Builder() {}

        public PayerHeaderContent build() {
            return new PayerHeaderContent();
        }
    }
}
