package com.p1g8.banking.model;

public class Transaction {
    private int transactionId;

    private int originAccountId;
    private int desintationAccountId;

    TransactionType type;
    private double amount;

    private String date;

    private Boolean status;
    private String note;
}
