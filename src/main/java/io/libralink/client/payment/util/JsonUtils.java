package io.libralink.client.payment.util;

import com.google.protobuf.Message;
import com.google.protobuf.TypeRegistry;
import com.google.protobuf.util.JsonFormat;
import io.libralink.client.payment.proto.Libralink;

public final class JsonUtils {

    private JsonUtils() {}

    public static <T extends Message> String toJson(T obj) throws Exception {
        TypeRegistry registry = TypeRegistry.newBuilder()
            .add(Libralink.ECheck.getDescriptor())
            .add(Libralink.ECheckSplit.getDescriptor())
            // add all types used inside Any
            .build();

        return JsonFormat.printer().usingTypeRegistry(registry).print(obj);
    }
}
