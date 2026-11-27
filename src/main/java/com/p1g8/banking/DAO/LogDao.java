package com.p1g8.banking.dao;

import com.p1g8.banking.model.Log;
import com.p1g8.banking.model.ActionType;

import java.time.Instant;
import java.util.List;

public interface LogDao {
    Log Create (Log log);
    List<Log> findByAccountId(long accountId);
    List<Log> findByEmployeeId(long employeeId);
    List<Log> findByActionType(ActionType type);
    List<Log> findByDateRange(Instant start, Instant end);
}
