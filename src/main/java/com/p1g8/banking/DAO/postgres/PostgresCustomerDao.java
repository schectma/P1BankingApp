package com.p1g8.banking.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.dao.CustomerDao;
import com.p1g8.banking.exception.DataAccessException;
import com.p1g8.banking.model.Customer;

public class PostgresCustomerDao implements CustomerDao {
    private final ConnectionProvider connectionProvider;
    public PostgresCustomerDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Customer create(Customer customer) {

        String sql = """
            INSERT INTO customers
            (first_name, last_name, user_name, password_hash)
            VALUES (?, ?, ?, ?)
            RETURNING id
            """;

        try (
                Connection conn = connectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPasswordHash());

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException(
                        "INSERT succeeded but no generated id was returned"
                    );
                }
                return new Customer(
                        rs.getLong("id"),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getUsername(),
                        customer.getPasswordHash()
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to create customer", e);
        }
    }

    @Override
    public Optional<Customer> findById(long customerId) {
        throw new UnsupportedOperationException();
    }
    public Optional<Customer> findByUsername(String username){
        throw new UnsupportedOperationException();
    }
    public boolean updateUsername(long customerId, String username){
        throw new UnsupportedOperationException();
    }
    public boolean updatePassword(long customerId, String passwordHash){
        throw new UnsupportedOperationException();
    }
}
