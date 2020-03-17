package com.rabobank.assignment.model;

import java.util.Objects;

/**
 * Representation of a failed customer transaction
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FailedTransaction that = (FailedTransaction) o;
        return Objects.equals(reference, that.reference) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, reason);
    }

    @Override
    public String toString() {
        return "FailedTransaction{" +
                "reference=" + reference +
                ", reason='" + reason + '\'' +
                '}';
    }
}
