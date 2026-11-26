package com.p1g8.banking.dao.postgres;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.config.PostgresConnectionProvider;
import com.p1g8.banking.config.PropertiesLoader;
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

        customer = new Customer(
                0,
                "firtus",
                "lastem",
                "userus",
                "passhash"
        );
    }

    @Test
    void createReturnsGeneratedId() {
        Customer created = dao.create(customer);
        assertTrue(created.getCustomerId() > 0);
    }
}
