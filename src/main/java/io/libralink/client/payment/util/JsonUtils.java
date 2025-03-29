package io.libralink.client.payment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {}

    public static <T> String toJson(T obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }
}
