package com.p1g8.banking.dao.mongo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoDatabase;
import com.p1g8.banking.config.MongoConfig;

public class MongoConnectionTest {
    @Test
    void canConnectToAtlas() throws IOException {

        Properties properties = new Properties();

        try (InputStream in =
            getClass()
                .getClassLoader()
                .getResourceAsStream(
                    "application.properties")) {

            assertNotNull(in);

            properties.load(in);
        }

        MongoConfig config = new MongoConfig(properties);

        MongoDatabase database = config.getDatabase();

        assertNotNull(database);

        database.listCollectionNames().first();

        assertDoesNotThrow(() ->
        database.listCollectionNames()
                .first());
    }
}
