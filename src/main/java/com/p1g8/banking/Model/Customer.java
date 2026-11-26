package com.p1g8.banking.model;

import java.time.Instant;

public class Customer{
    private long customerId;

    private String firstName;
    private String lastName;

    private String username;
    private String passwordHash;

    private Instant createdAt;

    public Customer(long customerId, String firstName, String lastName, String username, String passwordHash) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public long getCustomerId() { return customerId; }
    public String getFirstName() {return firstName; }
    public String getLastName() {return lastName; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
}
