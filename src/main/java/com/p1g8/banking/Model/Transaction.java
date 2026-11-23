package com.p1g8.banking.model;

import java.time.Instant;

public class Transaction {
    private int transactionId;

    private int accountId;
    private int counterpartyAccountId;

    TransactionType type;
    private double amount;
    private double resultingBalance;

    private long initiatedByEmployeeId;
    private Instant createdAt;
}
