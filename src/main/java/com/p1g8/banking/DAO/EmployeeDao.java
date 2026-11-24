package com.p1g8.banking.DAO;

import com.p1g8.banking.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    Employee create (Employee employee);
    Optional<Employee> findById(long employeeId);
    Optional<Employee> findByUserName(String userName);
    Employee update(Employee employee);

}
