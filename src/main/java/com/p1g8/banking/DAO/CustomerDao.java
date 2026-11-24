package com.p1g8.banking.dao;
import com.p1g8.banking.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Customer create(Customer customer);
    Optional<Customer> findById(long id);
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    Customer update(Customer customer);
    boolean deleteById(long id);
}
