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
            throw new IllegalStateException(
                "INSERT succeeded but no generated id was returned"
            );
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("user_name"),
                rs.getString("password_hash")
        );
    }

    @Override
    public Optional<Customer> findById(
            long customerId) {

        String sql = """
            SELECT *
            FROM customers
            WHERE id = ?
            """;

        try (
                Connection conn =
                        connectionProvider.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setLong(
                    1,
                    customerId);

            ResultSet rs =
                    stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(mapRow(rs));

        } catch (SQLException e) {

            throw new DataAccessException(
                    "Failed finding customer",
                    e);
        }
    }

    @Override
    public Optional<Customer> findByUsername(String username) {

        String sql = """
            SELECT *
            FROM customers
            WHERE user_name = ?
            """;

        try (
            Connection conn = connectionProvider.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    return Optional.empty();
                }

                return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {

            throw new DataAccessException(
                "Failed finding customer with username: "
                    + username, e
            );
        }
    }

    @Override
    public boolean updateUsername(long customerId, String username) {

        String sql = """
            UPDATE customers
            SET user_name = ?
            WHERE id = ?
            """;

        try (
            Connection conn = connectionProvider.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, username);
            stmt.setLong(2, customerId);

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {

            throw new DataAccessException(
                "Failed updating username for customer "
                        + customerId, e
            );
        }
    }

    public boolean updatePassword(long customerId, String passwordHash){
        throw new UnsupportedOperationException();
    }
}