package io.libralink.client.payment.protocol.envelope;

public enum SignatureReason {

    /**
     * No signature added - not everything needs to be signed
     */
    NONE,

    /**
     * Identity verification, i.e. to prove that Payer or Payee.
     * But this signature doesn't assume any financial obligations
     */
    IDENTITY,

    /**
     * Processor's signature to guarantee the Fee
     */
    FEE_LOCK,

    /**
     * Confirmation by Payer, Payee or Processor - assumes financial obligations
     */
    CONFIRM
}
