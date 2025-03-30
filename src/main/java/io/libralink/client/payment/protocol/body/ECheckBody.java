package io.libralink.client.payment.protocol.body;

import java.math.BigDecimal;

public class ECheckBody implements BodyContent {

    private final String objectType = ECheckBody.class.getSimpleName();
    private BigDecimal amount;
    private String type;
    private String payer;
    private String payerParty;
    private String payee;
    private String payeeParty;
    private long createdAt;
    private long expiresAt;
    private String note;

    private ECheckBody() {}

    public BigDecimal getAmount() {
        return amount;
    }

    void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public String getPayer() {
        return payer;
    }

    void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayerParty() {
        return payerParty;
    }

    void setPayerParty(String payerParty) {
        this.payerParty = payerParty;
    }

    public String getPayeeParty() {
        return payeeParty;
    }

    void setPayeeParty(String payeeParty) {
        this.payeeParty = payeeParty;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getNote() {
        return note;
    }

    void setNote(String note) {
        this.note = note;
    }

    public String getObjectType() {
        return objectType;
    }
}
