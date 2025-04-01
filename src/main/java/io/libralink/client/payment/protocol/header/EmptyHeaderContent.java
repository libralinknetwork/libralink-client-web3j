package io.libralink.client.payment.protocol.header;

public class EmptyHeaderContent implements HeaderContent {

    private EmptyHeaderContent() {}

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
