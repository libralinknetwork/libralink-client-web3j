package io.libralink.client.payment.protocol.header;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PartyHeaderContent.class, name = "PartyHeaderContent"),
        @JsonSubTypes.Type(value = PayeeHeaderContent.class, name = "PayeeHeaderContent"),
        @JsonSubTypes.Type(value = PayerHeaderContent.class, name = "PayerHeaderContent") }
)
public interface HeaderContent {
}
