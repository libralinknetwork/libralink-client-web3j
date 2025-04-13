package io.libralink.client.payment.protocol;

public enum SignatureAlgorithm {

    /**
     * Ethereum-style ECDSA keys
     */
    SECP256K1,

    /**
     * Post-quantum keys from the CRYSTALS-Dilithium suite - not supported at this stage
     */
    DILITHIUM5
}
