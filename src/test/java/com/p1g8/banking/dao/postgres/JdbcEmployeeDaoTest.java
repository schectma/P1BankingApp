package com.p1g8.banking.dao.postgres;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.config.PostgresConnectionProvider;
import com.p1g8.banking.config.PropertiesLoader;
import com.p1g8.banking.exception.DataAccessException;
import com.p1g8.banking.model.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcEmployeeDaoTest {

    private ConnectionProvider provider;
    private JdbcEmployeeDao dao;
    private long employeeId;
    private String userName;

    @BeforeEach
    void setUp() throws SQLException {

        provider = new PostgresConnectionProvider(
                PropertiesLoader.load());

        dao = new JdbcEmployeeDao(provider);

        userName = "test_" + UUID.randomUUID();

        String sql = """
                INSERT INTO employees
                (first_name, last_name, user_name, password_hash)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = provider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Test");
            ps.setString(2, "Employee");
            ps.setString(3, userName);
            ps.setString(4, "test_hash");

            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                employeeId = rs.getLong("id");
            }
        }
    }

    @AfterEach
    void tearDown() throws SQLException {

        if (provider == null || employeeId <= 0) {
            return;
        }

        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = provider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, employeeId);
            ps.executeUpdate();
        }
    }
    // PR 1: findById() test cases

    @Test
    void findByIdReturnsEmployee() {

        Optional<Employee> result = dao.findById(employeeId);

        assertTrue(result.isPresent());

        Employee employee = result.orElseThrow();

        assertEquals(employeeId, employee.getEmployeeId());
        assertEquals("Test", employee.getFirstName());
        assertEquals("Employee", employee.getLastName());
        assertEquals(userName, employee.getUserName());
        assertEquals("test_hash", employee.getPasswordHash());
        assertNotNull(employee.getCreatedAt());
    }

    @Test
    void findByIdReturnsEmptyWhenNotFound() {

        Optional<Employee> result = dao.findById(-1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByIdThrowsDataAccessException() {

        ConnectionProvider failingProvider = () -> {
            throw new SQLException("Simulated database failure");
        };

        JdbcEmployeeDao failingDao =
                new JdbcEmployeeDao(failingProvider);

        assertThrows(DataAccessException.class,
                () -> failingDao.findById(employeeId));
    }
    // PR 2: findByUserName() test cases
    @Test
    void findByUserNameReturnsEmployee() {

        Optional<Employee> result =
                dao.findByUserName(userName);

        assertTrue(result.isPresent());

        Employee employee = result.orElseThrow();

        assertEquals(employeeId, employee.getEmployeeId());
        assertEquals("Test", employee.getFirstName());
        assertEquals("Employee", employee.getLastName());
        assertEquals(userName, employee.getUserName());
        assertEquals("test_hash", employee.getPasswordHash());
        assertNotNull(employee.getCreatedAt());
    }

    @Test
    void findByUserNameReturnsEmptyWhenNotFound() {

        Optional<Employee> result =
                dao.findByUserName("missing_" + UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void findByUserNameThrowsDataAccessException() {

        ConnectionProvider failingProvider = () -> {
            throw new SQLException("Simulated database failure");
        };

        JdbcEmployeeDao failingDao =
                new JdbcEmployeeDao(failingProvider);

        assertThrows(DataAccessException.class,
                () -> failingDao.findByUserName(userName));
    }
}