package com.p1g8.banking.dao;

import com.p1g8.banking.model.BankAccount;
import com.p1g8.banking.model.AccountStatus;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public interface AccountDao {
    BankAccount create(BankAccount account);
    Optional<BankAccount> findById(long accountId);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    List<BankAccount> findByCustomerId(long customerId);
    boolean updateStatus(long accountId, AccountStatus status);
    boolean updateBalance(long accountId, BigDecimal balance);
    boolean deleteById(long accountId);
}
