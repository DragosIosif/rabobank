package com.rabobank.assignment.model;

public class FailedTransaction {
    private Long reference;
    private String reason;

    public FailedTransaction() {
    }

    public FailedTransaction(Long reference, String reason) {
        this.reference = reference;
        this.reason = reason;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
