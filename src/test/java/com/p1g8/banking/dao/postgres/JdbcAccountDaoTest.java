package com.p1g8.banking.dao.postgres;

import com.p1g8.banking.DAO.postgres.JdbcAccountDao;
import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.config.PostgresConnectionProvider;
import com.p1g8.banking.config.PropertiesLoader;
import com.p1g8.banking.model.BankAccount;
import com.p1g8.banking.model.AccountType;
import com.p1g8.banking.model.AccountStatus;
import com.p1g8.banking.exception.DuplicateDataException;
import com.p1g8.banking.exception.DataAccessException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
                generateAccountNumber(),
                AccountType.CHECKING,
                new BigDecimal("500.00"),
                AccountStatus.ACTIVE,
                Instant.now()
        );
    }

    private String generateAccountNumber(){
        return UUID.randomUUID().toString().replace("_", "").substring(0, 12);
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
    @Test
    void createRejectsInvalidCustomerId() {

        BankAccount invalidAccount = new BankAccount(
                0,
                -1L,
                generateAccountNumber(),
                AccountType.CHECKING,
                new BigDecimal("500.00"),
                AccountStatus.ACTIVE,
                Instant.now()
        );

        assertThrows(
                DataAccessException.class,
                () -> dao.create(invalidAccount)
        );
    }

    // 2. FIND BY ID TESTS
    @Test
    void findByIdReturnsAccount() {

        BankAccount created = dao.create(account);

        Optional<BankAccount> result =
                dao.findById(created.accountId());

        assertTrue(result.isPresent());

        assertEquals(
                created.accountId(),
                result.get().accountId()
        );

        assertEquals(
                created.accountNumber(),
                result.get().accountNumber()
        );
    }

    @Test
    void findByIdReturnsEmptyWhenNotFound() {

        Optional<BankAccount> result =
                dao.findById(-1L);

        assertTrue(result.isEmpty());
    }

    // 3. FIND BY ACCOUNT NUMBER TESTS
    @Test
    void findByAccountNumberReturnsAccount() {

        BankAccount created = dao.create(account);

        Optional<BankAccount> result =
                dao.findByAccountNumber(
                        created.accountNumber()
                );

        assertTrue(result.isPresent());

        assertEquals(
                created.accountId(),
                result.get().accountId()
        );

        assertEquals(
                created.accountNumber(),
                result.get().accountNumber()
        );
    }

    @Test
    void findByAccountNumberReturnsEmptyWhenNotFound() {

        Optional<BankAccount> result =
                dao.findByAccountNumber(
                        generateAccountNumber()
                );

        assertTrue(result.isEmpty());
    }

    // 4. FIND BY CUSTOMER ID TESTS

    @Test
    void findByCustomerIdReturnsMultipleAccounts() {

        BankAccount firstAccount = dao.create(account);

        BankAccount secondAccount = new BankAccount(
                0,
                customerId,
                generateAccountNumber(),
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountStatus.ACTIVE,
                Instant.now()
        );

        BankAccount createdSecond =
                dao.create(secondAccount);

        List<BankAccount> accounts =
                dao.findByCustomerId(customerId);

        assertEquals(2, accounts.size());

        assertTrue(accounts.stream().anyMatch(
                a -> a.accountId() == firstAccount.accountId()
        ));

        assertTrue(accounts.stream().anyMatch(
                a -> a.accountId() == createdSecond.accountId()
        ));

        assertTrue(accounts.stream().allMatch(
                a -> a.customerId() == customerId
        ));
    }

    @Test
    void findByCustomerIdReturnsEmptyList() {

        List<BankAccount> accounts =
                dao.findByCustomerId(customerId);

        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

    // 5. UPDATE STATUS TESTS

    @Test
    void updateStatusSuccessfully() {

        BankAccount created = dao.create(account);

        boolean updated = dao.updateStatus(
                created.accountId(),
                AccountStatus.CLOSED
        );

        assertTrue(updated);

        BankAccount updatedAccount =
                dao.findById(created.accountId())
                        .orElseThrow();

        assertEquals(
                AccountStatus.CLOSED,
                updatedAccount.status()
        );
    }

    @Test
    void updateStatusReturnsFalseWhenNotFound() {

        boolean updated = dao.updateStatus(
                -1L,
                AccountStatus.CLOSED
        );

        assertFalse(updated);
    }

    // 6. UPDATE BALANCE TESTS

    @Test
    void updateBalanceSuccessfully() {

        BankAccount created = dao.create(account);

        BigDecimal newBalance =
                new BigDecimal("1500.00");

        boolean updated = dao.updateBalance(
                created.accountId(),
                newBalance
        );

        assertTrue(updated);

        BankAccount updatedAccount =
                dao.findById(created.accountId())
                        .orElseThrow();

        assertEquals(
                0,
                newBalance.compareTo(updatedAccount.balance())
        );
    }

    @Test
    void updateBalanceReturnsFalseWhenNotFound() {

        boolean updated = dao.updateBalance(
                -1L,
                new BigDecimal("1000.00")
        );

        assertFalse(updated);
    }

    // 7. DELETE ACCOUNT TESTS

    @Test
    void deleteByIdSuccessfully() {

        BankAccount created = dao.create(account);

        boolean deleted =
                dao.deleteById(created.accountId());

        assertTrue(deleted);

        assertTrue(
                dao.findById(created.accountId()).isEmpty()
        );
    }

    @Test
    void deleteByIdReturnsFalseWhenNotFound() {

        boolean deleted = dao.deleteById(-1L);

        assertFalse(deleted);
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