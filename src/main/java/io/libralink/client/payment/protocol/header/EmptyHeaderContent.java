package io.libralink.client.payment.protocol.header;

public class EmptyHeaderContent implements HeaderContent {

    private final String objectType = EmptyHeaderContent.class.getSimpleName();

    private EmptyHeaderContent() {}

    public String getObjectType() {
        return objectType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private Builder() {}

        public EmptyHeaderContent build() {
            return new EmptyHeaderContent();
        }
    }
}
