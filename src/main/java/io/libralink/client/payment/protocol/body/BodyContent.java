package io.libralink.client.payment.protocol.body;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "objectType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = DepositReceiptBody.class, name = "DepositReceiptBody"),
    @JsonSubTypes.Type(value = PaymentRequestBody.class, name = "PaymentRequestBody"),
    @JsonSubTypes.Type(value = ECheckBody.class, name = "ECheckBody") }
)
public interface BodyContent {
}
