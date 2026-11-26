package com.p1g8.banking.config;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class PostgresConnectionProvider implements ConnectionProvider {

    private final String url;
    private final String username;
    private final String password;

    public PostgresConnectionProvider(Properties properties) {
        this.url = properties.getProperty("db.url");
        this.username = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            url,
            username,
            password);
    }
}
