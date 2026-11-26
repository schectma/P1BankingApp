package com.p1g8.banking.dao;

import com.p1g8.banking.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    Optional<Employee> findById(long employeeId);
    Optional<Employee> findByUserName(String userName);

}
