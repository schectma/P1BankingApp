package com.p1g8.banking.Model;

import java.math.BigDecimal;
import java.time.Instant;

public record BankAccount(long id,
                          long customerId,
                          String accountNumber,
                          AccountType type,
                          BigDecimal balance,
                          AccountStatus status,
                          Instant createdAT) {

}
