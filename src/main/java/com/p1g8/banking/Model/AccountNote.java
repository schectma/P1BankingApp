package com.p1g8.banking.model;

import java.time.Instant;
import java.util.List;

public class AccountNote {
    private long id;
    private long accountId;
    private long employeeId;
    private String text;
    private List<String> tags;
    private Long relatedTransactionId;
    private Instant createdAt;

    public AccountNote(long id, long accountId, long employeeId, String text, List<String> tags, Long relatedTransactionId, Instant createdAT) {
        this.id = id;
        this.accountId = accountId;
        this.employeeId = employeeId;
        this.text = text;
        this.tags = tags;
        this.relatedTransactionId = relatedTransactionId;
        this.createdAT = createdAT;
    }

    public long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getText() {
        return text;
    }

    public List<String> getTags() {
        return tags;
    }

    public Long getRelatedTransactionId() {
        return relatedTransactionId;
    }

    public Instant getCreatedAt() {
        return createdAT;
    }
}
