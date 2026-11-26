package com.p1g8.banking.model;

import java.time.Instant;

public class Customer{
    private long customerId;

    private String firstName;
    private String lastName;

    private String username;
    private String passwordHash;

    private Instant createdAt;

    public Customer(long customerId, String username, String passwordHash) {
        this.customerId = customerId;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public long getCustomerId() { return customerId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
}
