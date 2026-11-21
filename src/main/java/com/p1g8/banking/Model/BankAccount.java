package com.p1g8.banking.model;

import java.math.BigDecimal;
import java.time.Instant;

public record BankAccount(long accountId,
                          long customerId,
                          String accountNumber,
                          AccountType type,
                          BigDecimal balance,
                          AccountStatus status,
                          Instant createdAT) {

}
