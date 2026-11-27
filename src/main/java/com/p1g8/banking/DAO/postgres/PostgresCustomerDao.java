package com.p1g8.banking.dao.postgres;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.dao.CustomerDao;

public abstract class PostgresCustomerDao implements CustomerDao {
    private final ConnectionProvider connectionProvider;
    public PostgresCustomerDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }
}
