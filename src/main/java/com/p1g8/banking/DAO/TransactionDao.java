package com.p1g8.banking.dao;

import com.p1g8.banking.model.Transaction;
import com.p1g8.banking.model.TransactionType;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    Transaction create(Transaction transaction);
    Optional<Transaction> findById(long transactionId);
    List<Transaction> findByAccountId(long accountId);
    List<Transaction> findByAccountIdAndDateRange(long accountId, Instant start, Instant end);
    List<Transaction> findByAccountIdAndType(long accountId, TransactionType type);
}
