package io.libralink.client.payment.util;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public final class EncryptionUtils {

    private EncryptionUtils() {}

    public static Credentials create(String privateKey) {
        return Credentials.create(privateKey);
    }

    public static String sign(byte[] message, Credentials credentials) {
        Sign.SignatureData sigData = Sign.signPrefixedMessage(message, credentials.getEcKeyPair());
        String r = Numeric.toHexString(sigData.getR());
        String s = Numeric.toHexString(sigData.getS()).substring(2);
        String v = Numeric.toHexString(sigData.getV()).substring(2);
        return r + s + v;
    }

    /**
     * Given a message, the signature of this message and an Ethereum address, checks if the address is the signer
     * @param message The message that was signed.
     * @param hexStringSignature The signature, in hexadecimal string
     * @param hexStringEthAddress The Ethereum address, in hexadecimal string
     * @return true if Ethereum address is the signer of message that creates the signature
     */
    public static boolean recover(byte[] message, String hexStringSignature, String hexStringEthAddress) throws Exception {

        if(hexStringSignature.length() != 132 || !hexStringSignature.startsWith("0x")) {
            throw new Exception("Signature must be an hexadecimal string starting with 0x + 130 characters");
        }

        // Split signature in 3 parts
        // The R, S part of any ECDSA signature, and the specific Ethereum V part.
        // Signature is as follows :
        // [0-1]: hexadecimal prefix (0x), skipped when splitting
        // [2-65]: R, 64 hexa characters, 32 bytes
        // [66-129]: S, 64 hexa characters, 32 bytes
        // [130-131]: V, 2 hexa characters, 1 byte
        String r = hexStringSignature.substring(2, 66);
        String s = hexStringSignature.substring(66, 130);
        String v = hexStringSignature.substring(130, 132);

        // Then we need to cast hexadecimal strings R, S & V into bytes arrays to create a signature object
        Sign.SignatureData signatureData = new Sign.SignatureData(Numeric.hexStringToByteArray(v), Numeric.hexStringToByteArray(r), Numeric.hexStringToByteArray(s));

        // Use Web3j framework to retrieve public key that has signed the message. Input message has to be cast from decimal alphabet to bytes array
        // Then, message will be wrapped as follows:
        // 1. message = "\u0019Ethereum Signed Message:\n" + message.length + message
        // 2. message = sha3(message)
        BigInteger pubKey = Sign.signedPrefixedMessageToKey(message, signatureData);

        // Then retrieve the Ethereum address derived from the public key
        String recover = Keys.getAddress(pubKey);

        // Add the hexadecimal prefix to turn it back to usual Ethereum address
        recover = "0x" + recover;

        // The given address should match the parameter
        return recover.equalsIgnoreCase(hexStringEthAddress);
    }
}
