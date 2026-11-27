package com.p1g8.banking.model;

import java.time.Instant;
import java.util.List;

public class AccountNote {
    private final long id;
    private final long accountId;
    private final long employeeId;
    private final String text;
    private final List<String> tags;
    private final Long relatedTransactionId;
    private final Instant createdAt;

    public AccountNote(long id, long accountId, long employeeId, String text, List<String> tags, Long relatedTransactionId, Instant createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.employeeId = employeeId;
        this.text = text;
        this.tags = List.copyOf(tags);
        this.relatedTransactionId = relatedTransactionId;
        this.createdAt = createdAt;
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
        return createdAt;
    }
}
