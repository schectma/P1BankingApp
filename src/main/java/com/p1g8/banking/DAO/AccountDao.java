package com.p1g8.banking.dao;

import com.p1g8.banking.model.BankAccount;
import com.p1g8.banking.model.AccountStatus;

import java.util.List;
import java.util.Optional;

public interface AccountDao {
    BankAccount create(BankAccount account);
    Optional<BankAccount> findById(long id);
    List<BankAccount> findByCustomerId(long customerId);
    List<BankAccount> findByStatus(AccountStatus status);
    List<BankAccount> findAll();
    BankAccount update(BankAccount account);
    boolean updateStatus(long accountId, AccountStatus status);
    boolean deleteById(long accountId);
}
