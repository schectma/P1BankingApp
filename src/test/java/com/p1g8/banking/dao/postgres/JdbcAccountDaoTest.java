package com.p1g8.banking.dao.postgres;

import com.p1g8.banking.DAO.postgres.JdbcAccountDao;
import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.config.PostgresConnectionProvider;
import com.p1g8.banking.config.PropertiesLoader;
import com.p1g8.banking.model.BankAccount;
import com.p1g8.banking.model.AccountType;
import com.p1g8.banking.model.AccountStatus;
import com.p1g8.banking.exception.DuplicateDataException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.Properties;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcAccountDaoTest {

    private JdbcAccountDao dao;
    private ConnectionProvider provider;
    private long customerId;
    private BankAccount account;

    @BeforeEach
    void setUp() throws SQLException {

        Properties props = PropertiesLoader.load();
        provider = new PostgresConnectionProvider(props);
        dao = new JdbcAccountDao(provider);

        String sql = """
                INSERT INTO customers
                (first_name, last_name, user_name, password_hash)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = provider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Test");
            ps.setString(2, "Customer");
            ps.setString(3, "test_" + UUID.randomUUID());
            ps.setString(4, "test_hash");

            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                customerId = rs.getLong("id");
            }
        }

        account = new BankAccount(
                0,
                customerId,
                UUID.randomUUID().toString().substring(0, 12),
                AccountType.CHECKING,
                new BigDecimal("500.00"),
                AccountStatus.ACTIVE,
                Instant.now()
        );
    }

    @Test
    void createReturnsGeneratedId() {

        BankAccount created = dao.create(account);

        assertNotNull(created);
        assertTrue(created.accountId() > 0);
    }

    @Test
    void createReturnsCorrectAccountDetails() {

        BankAccount created = dao.create(account);

        assertEquals(customerId, created.customerId());
        assertEquals(account.accountNumber(),
                created.accountNumber());
        assertEquals(account.type(), created.type());
        assertEquals(account.status(), created.status());
        assertEquals(0,
                account.balance().compareTo(created.balance()));
        assertNotNull(created.createdAT());
    }

    @Test
    void createRejectsDuplicateAccountNumber() {

        dao.create(account);

        assertThrows(DuplicateDataException.class,
                () -> dao.create(account));
    }

    @AfterEach
    void tearDown() throws SQLException {

        if (provider == null || customerId <= 0) {
            return;
        }

        try (Connection conn = provider.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM bank_accounts WHERE customer_id = ?")) {

                ps.setLong(1, customerId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM customers WHERE id = ?")) {

                ps.setLong(1, customerId);
                ps.executeUpdate();
            }
        }
    }
}