package com.p1g8.banking.DAO;

import com.p1g8.banking.model.Transaction;
import com.p1g8.banking.model.TransactionType;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    Transaction create(Transaction transaction);
    Optional<Transaction> findById(long transactionId);
    List<Transaction> findByAccountId(long accountId);
    List<Transaction> findByEmployeeId(long employeeId);
    List<Transaction> findByDateRange(long accountId, Instant start, Instant end);
    List<Transaction> findByAccountIdandType(long accountId, TransactionType type);
    List<Transaction> findAll();
}
