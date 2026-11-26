package com.p1g8.banking.model;

import java.time.Instant;

public class Log {
    private long employeeId;
    private long customerId;
    private long accountId;

    private ActionType actionType;

    private String details;
    private Instant createdAt;
}
