package com.p1g8.banking.config;

import java.sql.SQLException;
import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection() throws SQLException;
}
