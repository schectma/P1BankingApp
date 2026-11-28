package com.p1g8.banking.model;

import java.time.Instant;

public class Log {
    private long id;
    private long employeeId;
    private long customerId;
    private long accountId;

    private ActionType actionType;

    private String details;
    private Instant createdAt;

    public Log(
            long id,
            long employeeId,
            long customerId,
            long accountId,
            ActionType actionType,
            String details,
            Instant createdAt) {

        this.id = id;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.actionType = actionType;
        this.details = details;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public String getDetails() {
        return details;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
