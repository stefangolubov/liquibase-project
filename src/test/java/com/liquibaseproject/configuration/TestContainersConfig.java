package com.liquibaseproject.configuration;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestConfiguration
public class TestContainersConfig {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("my_password");

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return postgreSQLContainer;
    }
}