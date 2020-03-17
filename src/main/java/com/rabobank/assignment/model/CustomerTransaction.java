package com.rabobank.assignment.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Representation of a customer transaction
 */
public class CustomerTransaction {
    private Long reference;
    private String accountNumber;
    private Double startBalance;
    private Double mutation;
    private String description;
    private Double endBalance;
    private boolean processed;
    private boolean valid;
    private Set<String> reasons;

    public CustomerTransaction() {
        reasons = new HashSet<>();
        valid = true;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getMutation() {
        return mutation;
    }

    public void setMutation(Double mutation) {
        this.mutation = mutation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(Double endBalance) {
        this.endBalance = endBalance;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Set<String> getReasons() {
        return reasons;
    }

    public void setReasons(Set<String> reasons) {
        this.reasons = reasons;
    }

    public void addReason(String reason) {
        this.reasons.add(reason);
    }

    @Override
    public String toString() {
        return "CustomerStatement{" +
                "reference=" + reference +
                ", accountNumber='" + accountNumber + '\'' +
                ", startBalance=" + startBalance +
                ", mutation=" + mutation +
                ", description='" + description + '\'' +
                ", endBalance=" + endBalance +
                ", processed=" + processed +
                ", valid=" + valid +
                ", reason='" + reasons + '\'' +
                '}';
    }
}
