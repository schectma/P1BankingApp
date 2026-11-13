package com.p1g8.banking.Model;

import java.time.Instant;
import java.util.List;

public class AccountNote {
    private String id;
    private long accountId;
    private long employeeId;
    private String text;
    private List<String> tags;
    private Long relatedTransactionId;
    private Instant createdAt;

    public AccountNote(String id, long accountId, long employeeId, String text, List<String> tags, Long relatedTransactionId, Instant createdAT) {
        this.id = id;
        this.accountId = accountId;
        this.employeeId = employeeId;
        this.text = text;
        this.tags = tags;
        this.relatedTransactionId = relatedTransactionId;
        this.createdAT = createdAT;
    }

    public String getId() {
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
