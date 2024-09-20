package com.liquibaseproject.it;

import com.jayway.jsonpath.JsonPath;
import com.liquibaseproject.LiquibaseProjectApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LiquibaseProjectApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Slf4j
class LiquibaseProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static String createdUserId;

    private static final String newUserJson = "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\" }";
    private static final String newUserJson2 = "{ \"username\": \"testuser2\", \"email\": \"testuser2@example.com\" }";

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Adding user with role ADMIN")
    void testAddUser() throws Exception {
        log.info("Creating user with username testuser");

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        createdUserId = JsonPath.parse(responseContent).read("$.id");
        log.info("User with ID {} has been created ", createdUserId);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Trying to add a user with role USER")
    void testAddUserFails() throws Exception {
        log.info("Trying to create a user with role USER");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("You don't have access to perform this operation")).andReturn();

        log.info("Creation of a user with role USER failed!");
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Finding all users")
    void testFindAllUsers() throws Exception {
        log.info("Finding all users");
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        log.info("All users have been found");
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Finding users by username")
    void testFindUsersByUsername() throws Exception {

        mockMvc.perform(get("/users")
                        .param("usernames", "testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson2))
                .andReturn();

        mockMvc.perform(get("/users")
                        .param("usernames", "testuser,testuser2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[1].username").value("testuser2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Updating user with role ADMIN")
    void testUpdateUser() throws Exception {
        if (StringUtils.isEmpty(createdUserId)) {
            log.info("Creating user to be updated later");
            testAddUser();
        }

        String updatedUserJson = "{ \"id\": \"" + createdUserId + "\", \"username\": \"updateduser\", \"email\": \"updateduser@example.com\" }";

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User has been successfully updated"));

        log.info("User with ID {} has been successfully updated with username {}", createdUserId, "updateduser");

        mockMvc.perform(get("/users/" + createdUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("updateduser@example.com"));
        log.info("Updated user has been successfully verified");
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Trying to delete user with role USER")
    void testDeleteUserFails() throws Exception {
        mockMvc.perform(delete("/users/{id}", createdUserId))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("You don't have access to perform this operation")).andReturn();

        log.info("Deleting user with ID {} with role USER failed!", createdUserId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deleting user with role ADMIN")
    void testDeleteUser() throws Exception {
        if (StringUtils.isEmpty(createdUserId)) {
            log.info("Creating user to be deleted later");
            testAddUser();
        }

        mockMvc.perform(delete("/users/{id}", createdUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User has been successfully deleted"));

        log.info("User with ID {} has been deleted ", createdUserId);
    }
}