package io.libralink.client;

import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.Assert.assertTrue;

public class PostQuantumCryptographicTest {

    @Test
    public void test_verify_signature() throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        /*
         * Generate ML-DSA key pair
         * ml_dsa_44 (Dilithium2): Smallest size, fastest — good for IoT, smart cards, mobile. (NIST Level 1, Cost ~2^164 (post-quantum))
         * ml_dsa_65 (Dilithium3): Balanced size vs. strength — good default for most apps. (NIST Level 3, Cost ~2^192)
         * ml_dsa_87 (Dilithium5): Strongest, but largest and slowest — only use if you really need it. (NIST Level 5, Cost ~2^256)
         */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ML-DSA", "BC");
        keyPairGenerator.initialize(MLDSAParameterSpec.ml_dsa_65, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Sign data
        byte[] data = "This is the data to be signed".getBytes();
        Signature signature = Signature.getInstance("ML-DSA", "BC");
        signature.initSign(keyPair.getPrivate());
        signature.update(data);
        byte[] sigBytes = signature.sign();

        // Verify signature
        Signature verifier = Signature.getInstance("ML-DSA", "BC");
        verifier.initVerify(keyPair.getPublic());
        verifier.update(data);
        boolean verified = verifier.verify(sigBytes);

        assertTrue(verified);

        // Example of key export and import
        String publicKeyEncoded = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKeyEncoded = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String sigEncoded = Base64.getEncoder().encodeToString(sigBytes);

        System.out.println("Encoded Public Key (len " + publicKeyEncoded.length() + "): " + publicKeyEncoded);
        System.out.println("Encoded Private Key (len " + privateKeyEncoded.length() + "): " + privateKeyEncoded);
        System.out.println("Signature (len " + sigEncoded.length() + "): " + sigEncoded);

        KeyFactory keyFactory = KeyFactory.getInstance("ML-DSA", "BC");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyEncoded));
        PublicKey importedPublicKey = keyFactory.generatePublic(publicKeySpec);

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyEncoded));
        PrivateKey importedPrivateKey = keyFactory.generatePrivate(privateKeySpec);

        // Verify signature with imported key
        verifier.initVerify(importedPublicKey);
        verifier.update(data);
        boolean verifiedImportedKey = verifier.verify(sigBytes);
        assertTrue(verifiedImportedKey);
    }
}
