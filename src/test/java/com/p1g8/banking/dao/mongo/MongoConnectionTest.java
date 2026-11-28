package com.p1g8.banking.dao.mongo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.p1g8.banking.config.MongoConfig;
import org.bson.Document;

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

                        String firstCollection =
        database.listCollectionNames()
                .first();

        System.out.println(
        "Connected to Atlas. First collection: "
                + firstCollection);

        long count =
        database.listCollectionNames()
                .into(new ArrayList<>())
                .size();

        System.out.println(
                "Atlas returned "
                        + count
                        + " collections.");

        MongoCollection<Document> notes =
        database.getCollection(
                "account_notes");

        notes.find().first();
    }
}
