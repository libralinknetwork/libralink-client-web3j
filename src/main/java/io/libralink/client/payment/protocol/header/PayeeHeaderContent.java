package io.libralink.client.payment.protocol.header;

public class PayeeHeaderContent implements HeaderContent {

    private final String objectType = PayeeHeaderContent.class.getSimpleName();

    private PayeeHeaderContent() {}

    public String getObjectType() {
        return objectType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private Builder() {}

        public PayeeHeaderContent build() {
            return new PayeeHeaderContent();
        }
    }
}
