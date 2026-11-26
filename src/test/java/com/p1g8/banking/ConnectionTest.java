package com.p1g8.banking;

import java.util.Properties;
import java.sql.Connection;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.config.PostgresConnectionProvider;
import com.p1g8.banking.config.PropertiesLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConnectionTest {

    @Test
    void connects() throws Exception {
        Properties props = PropertiesLoader.load();
        ConnectionProvider provider = new PostgresConnectionProvider(props);

        try (Connection conn = provider.getConnection()) {
            assertTrue(conn.isValid(5));
            System.out.println("Connected!");
        }
    }
}
