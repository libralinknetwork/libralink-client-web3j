package io.libralink.client.payment.signature;

import io.libralink.client.payment.protocol.Envelope;
import io.libralink.client.payment.protocol.body.BodyEnvelope;
import io.libralink.client.payment.protocol.header.HeaderWithSignature;
import io.libralink.client.payment.protocol.header.PartyHeaderContent;
import io.libralink.client.payment.protocol.header.EmptyHeaderContent;
import io.libralink.client.payment.util.EncryptionUtils;
import io.libralink.client.payment.util.JsonUtils;
import org.web3j.crypto.Credentials;

import java.util.List;

import static io.libralink.client.payment.util.EncryptionUtils.recover;

public final class SignatureHelper {

    private SignatureHelper() {}

    public static boolean verify(Envelope envelope) throws Exception {

        boolean isValid = true;

        BodyEnvelope body = envelope.getBody();
        List<HeaderWithSignature> headers = envelope.getHeader().getHeaders();

        for (HeaderWithSignature header: headers) {
            String bodyAddress = header.getBodySig().getAddress();
            String bodySignature = header.getBodySig().getSig();

            isValid = isValid && verify(body, bodyAddress, bodySignature);

            /* Party Header signature needs to be verified */
            if (PartyHeaderContent.class == header.getContent().getClass()) {
                String headerAddress = header.getHeaderSig().getAddress();
                String headerSignature = header.getHeaderSig().getSig();

                isValid = isValid && verify((PartyHeaderContent) header.getContent(), headerAddress, headerSignature);
            }
        }

        return isValid;
    }

    public static String sign(BodyEnvelope envelope, Credentials credentials) throws Exception {
        String json = JsonUtils.toJson(envelope);
        return EncryptionUtils.sign(json, credentials);
    }

    public static boolean verify(BodyEnvelope envelope, String address, String signature) throws Exception {
        String json = JsonUtils.toJson(envelope);
        return recover(json, signature, address);
    }

    public static String sign(PartyHeaderContent content, Credentials credentials) throws Exception {
        String json = JsonUtils.toJson(content);
        return EncryptionUtils.sign(json, credentials);
    }

    public static boolean verify(PartyHeaderContent content, String address, String signature) throws Exception {
        String json = JsonUtils.toJson(content);
        return recover(json, signature, address);
    }

    /**
     * PayeeHeaderContent is empty, nothing to sign
     */
    public static String sign(EmptyHeaderContent content, Credentials credentials) throws Exception {
        return null;
    }

    public static boolean verify(EmptyHeaderContent content, String address, String signature) throws Exception {
        return signature == null;
    }
}
