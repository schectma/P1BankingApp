package com.p1g8.banking.dao.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.config.PostgresConnectionProvider;
import com.p1g8.banking.config.PropertiesLoader;
import com.p1g8.banking.exception.DataAccessException;
import com.p1g8.banking.model.Customer;

public class PostgresCustomerDaoTest {
    
    private PostgresCustomerDao dao;
    private Customer customer;

    @BeforeEach
    void setUp() {

        Properties props = PropertiesLoader.load();

        ConnectionProvider provider =
                new PostgresConnectionProvider(props);

        dao = new PostgresCustomerDao(provider);

        String uniqueUsername = "test_" + UUID.randomUUID();

        customer = new Customer(
                0,
                "firtus",
                "lastem",
                uniqueUsername,
                "passhash"
        );
    }

    @Test
    void createReturnsGeneratedId() {
        Customer created = dao.create(customer);
        assertTrue(created.getCustomerId() > 0);
    }

    /* findById() */

    @Test
    void findByIdReturnsCustomer() {

        Customer created = dao.create(customer);

        Optional<Customer> found =dao.findById(
            created.getCustomerId());

        assertTrue(found.isPresent());

        Customer retrieved = found.get();

        assertEquals(
            created.getCustomerId(),
            retrieved.getCustomerId());

        assertEquals(
            customer.getUsername(),
            retrieved.getUsername());
    }

    @Test
    void findByIdReturnsEmptyWhenCustomerMissing() {

        Optional<Customer> found = dao.findById(Long.MAX_VALUE);

        assertFalse(found.isPresent());
    }

    /* findByUsername() */
    @Test
    void findByUsernameReturnsCustomer() {

        Customer created = dao.create(customer);

        Optional<Customer> found =
            dao.findByUsername(created.getUsername());

        assertTrue(found.isPresent());

        Customer retrieved = found.get();

        assertEquals(
            created.getCustomerId(),
            retrieved.getCustomerId());

        assertEquals(
            created.getUsername(),
            retrieved.getUsername());
    }

    @Test
    void findByUsernameReturnsEmptyWhenMissing() {
        Optional<Customer> found = dao.findByUsername(
                "definitely_not_a_real_user");

        assertTrue(found.isEmpty());
    }

    /* updateUsername() */
    @Test
    void updateUsernameChangesUsername() {

        Customer created = dao.create(customer);

        String uniqueUsername = "updated_" + UUID.randomUUID();

        boolean updated =
            dao.updateUsername(
                created.getCustomerId(),
                uniqueUsername);

        assertTrue(updated);

        Optional<Customer> found =
            dao.findById(
                created.getCustomerId());

        assertTrue(found.isPresent());

        assertEquals(
            uniqueUsername,
            found.get().getUsername());
    }

    @Test
    void updateUsernameReturnsFalseWhenCustomerMissing() {

        boolean updated =
            dao.updateUsername(
                Long.MAX_VALUE,
                "updatedUser");

        assertFalse(updated);
    }

    @Test
    void updateUsernameRejectsDuplicateUsername() {

        String uniqueUsername = "updated_" + UUID.randomUUID();

        Customer first = dao.create(customer);

        Customer second = dao.create(
            new Customer(
                0,
                "Second",
                "User",
                uniqueUsername,
                "passhash"
            )
        );

        assertThrows(
            DataAccessException.class,
            () -> dao.updateUsername(
                second.getCustomerId(),
                first.getUsername()
            )
        );
    }
}