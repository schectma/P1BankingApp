package com.p1g8.banking.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    
    private static final String FILE_NAME = "application.properties";

    private PropertiesLoader() {
    }

    public static Properties load() {

        Properties properties = new Properties();

        try (InputStream input = PropertiesLoader.class
            .getClassLoader().getResourceAsStream(FILE_NAME)) {

                if (input == null) {
                    throw new RuntimeException(FILE_NAME + " not found");
                }

                properties.load(input);

                return properties;

            } catch (IOException e) {
                throw new RuntimeException(
                    "Failed to load properties", e);
            }
    }
}
