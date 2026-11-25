package com.p1g8.banking.DAO;

import com.p1g8.banking.model.Log;
import com.p1g8.banking.model.TransactionType;

import java.time.Instant;
import java.util.List;

public interface LogDao {
    Log Create (Log log);
    Optional<Log> findByAccountId(long accountId);
    Optional<Log> findByEmployeeId(long employeeId);
    Optional<Log> findByCustomerId(long customerId);
    List<Log> findByTransactionType(TransactionType transactionType);
    List<Log> findByDateRange(Instant start, Instant end);
}
