package com.liquibaseproject.integration;

import com.liquibaseproject.LiquibaseProjectApplication;
import com.liquibaseproject.configuration.TestContainersConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LiquibaseProjectApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("testcontainers")
@ContextConfiguration(classes = {TestContainersConfig.class})
@Slf4j
class LiquibaseProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void cleanDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        log.info("Database cleanup");
        jdbcTemplate.execute("DELETE FROM ORDERS");
        jdbcTemplate.execute("DELETE FROM PRODUCTS");
        jdbcTemplate.execute("DELETE FROM USERS");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddUser() throws Exception {
        String newUserJson = "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\" }";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testFindAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}