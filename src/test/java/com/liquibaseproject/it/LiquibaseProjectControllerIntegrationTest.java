package com.liquibaseproject.it;

import com.jayway.jsonpath.JsonPath;
import com.liquibaseproject.LiquibaseProjectApplication;
import com.liquibaseproject.entity.Products;
import com.liquibaseproject.service.ProductsService;
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

import java.util.UUID;

import static com.liquibaseproject.constant.ExceptionConstants.DATA_INTEGRITY_VIOLATION_USER_MESSAGE;
import static com.liquibaseproject.constant.ExceptionConstants.NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Autowired
    private ProductsService productsService;

    private static String createdUserId;
    private static final String newUserJson = "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\" }";
    private static final String newUserJson2 = "{ \"username\": \"testuser2\", \"email\": \"testuser2@example.com\" }";
    private static final String newUserJson3 = "{ \"username\": \"testuser3\", \"email\": \"testuser3@example.com\" }";

    private static String createdProductId;
    private static final String newProductJson = "{ \"name\": \"testproduct\", \"description\": \"testproductdescription\", \"price\": \"500.0\", \"quantity\": \"35\" }";
    private static final String newProductJson2 = "{ \"name\": \"testproduct2\", \"description\": \"testproductdescription2\", \"price\": \"50.0\", \"quantity\": \"3\" }";
    private static final String newProductJson3 = "{ \"name\": \"testproduct3\", \"description\": \"testproductdescription3\", \"price\": \"1500.0\", \"quantity\": \"12\" }";

    private static String createdOrderId;

    // Users

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
                .andExpect(jsonPath("$.message").value(NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE)).andReturn();

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
    void testFindUsersByUsernames() throws Exception {
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
                        .param("usernames", "TestUser,testUseR2")
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

        log.info("User with ID {} has been successfully updated with username updateduser", createdUserId);

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
                .andExpect(jsonPath("$.message").value(NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE)).andReturn();

        log.info("Deleting user with ID {} with role USER failed!", createdUserId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deleting user with role ADMIN fails because of an active order")
    void testDeleteUserFailsSinceThereIsAnActiveOrderCreated() throws Exception {
        if (StringUtils.isEmpty(createdUserId)) {
            log.info("Creating user to be deleted later");
            testAddUser();
        }

        mockMvc.perform(delete("/users/{id}", createdUserId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(DATA_INTEGRITY_VIOLATION_USER_MESSAGE));

        log.info("User with ID {} cannot be deleted since there is an active order created by the user.", createdUserId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deleting user with role ADMIN")
    void testDeleteUser() throws Exception {
        testAddUser();

        mockMvc.perform(delete("/users/{id}", createdUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User has been successfully deleted"));

        log.info("User with ID {} has been successfully deleted.", createdUserId);
    }

    //Products

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Adding product with role ADMIN")
    void testAddProduct() throws Exception {
        log.info("Creating product with name testproduct");

        MvcResult result = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testproduct"))
                .andExpect(jsonPath("$.description").value("testproductdescription"))
                .andExpect(jsonPath("$.price").value("500.0"))
                .andExpect(jsonPath("$.quantity").value("35"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        createdProductId = JsonPath.parse(responseContent).read("$.id");

        log.info("Product with ID {} has been created ", createdProductId);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Trying to add a product with role USER")
    void testAddProductFails() throws Exception {
        log.info("Trying to create a product with role USER");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE)).andReturn();

        log.info("Creation of a product with role USER failed!");
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Finding all products")
    void testFindAllProducts() throws Exception {
        log.info("Finding all products");

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        log.info("All products have been found");
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Finding products by name")
    void testFindProductsByName() throws Exception {
        if (StringUtils.isEmpty(createdProductId)) {
            log.info("Creating product");
            testAddProduct();
        }

        mockMvc.perform(get("/products")
                        .param("names", "testproduct")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("testproduct"));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson2))
                .andReturn();

        mockMvc.perform(get("/products")
                        .param("names", "testproducT,testProduct2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("testproduct"))
                .andExpect(jsonPath("$[1].name").value("testproduct2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Updating product with role ADMIN")
    void testUpdateProduct() throws Exception {
        if (StringUtils.isEmpty(createdProductId)) {
            log.info("Creating product to be updated later");
            testAddUser();
        }

        String updatedProductJson = "{ \"id\": \"" + createdProductId + "\", \"name\": \"updatedproduct\", \"description\": \"updatedproductdescription\", \"quantity\": \"80\", \"price\": \"550.0\" }";

        mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product has been successfully updated"));
        log.info("Product with ID {} has been successfully updated with name {}", createdProductId, "updatedproduct");

        mockMvc.perform(get("/products/" + createdProductId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updatedproduct"))
                .andExpect(jsonPath("$.description").value("updatedproductdescription"))
                .andExpect(jsonPath("$.quantity").value("80"))
                .andExpect(jsonPath("$.price").value("550.0"));
        log.info("Updated product has been successfully verified");
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Trying to delete product with role USER")
    void testDeleteProductFails() throws Exception {
        mockMvc.perform(delete("/products/{id}", "44a06a54-0c1f-40a3-883e-54cb0d215f75"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE)).andReturn();

        log.info("Deleting product with ID {} with role USER failed!", createdProductId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deleting product with role ADMIN")
    void testDeleteProduct() throws Exception {
        if (StringUtils.isEmpty(createdProductId)) {
            log.info("Creating product to be deleted later");
            testAddProduct();
        }

        mockMvc.perform(delete("/products/{id}", createdProductId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product has been successfully deleted"));

        log.info("Product with ID {} has been deleted ", createdProductId);
    }

    // Orders

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Placing order with role ADMIN")
    void testPlaceOrder() throws Exception {
        if (StringUtils.isEmpty(createdUserId)) {
            log.info("Creating user before placing an order");
            testAddUser();
        }

        if (StringUtils.isEmpty(createdProductId)) {
            log.info("Creating product before placing an order");
            testAddProduct();
        }

        log.info("Placing order");

        Products productBefore = productsService.getProductById(UUID.fromString(createdProductId)).orElseThrow(() -> new Exception("Product not found"));
        int quantityBefore = productBefore.getQuantity();

        String newOrderJson = String.format("{ \"userId\": \"%s\", \"productId\": \"%s\", \"quantity\": 1 }", createdUserId, createdProductId);

        MvcResult result = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newOrderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(createdUserId))
                .andExpect(jsonPath("$.productId").value(createdProductId))
                .andExpect(jsonPath("$.quantity").value("1"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        createdOrderId = JsonPath.parse(responseContent).read("$.id");

        log.info("Order with ID {} has been placed ", createdOrderId);

        Products productAfter = productsService.getProductById(UUID.fromString(createdProductId)).orElseThrow(() -> new Exception("Product not found"));
        int quantityAfter = productAfter.getQuantity();

        // Verify that the product quantity has been updated correctly
        assertEquals(quantityBefore - 1, quantityAfter, "Product quantity should be updated correctly after order cancellation");
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Trying to add a product with role USER")
    void testPlaceOrderFails() throws Exception {
        log.info("Trying to place an order with role USER");

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE)).andReturn();

        log.info("Placing order with role USER failed!");
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    @DisplayName("Finding all orders")
    void testFindAllOrders() throws Exception {
        log.info("Finding all orders");
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        log.info("All orders have been found");
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Finding orders by order IDs")
    void testFindOrdersByIDs() throws Exception {
        if (StringUtils.isEmpty(createdOrderId)) {
            testPlaceOrder();
        }

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser3"))
                .andExpect(jsonPath("$.email").value("testuser3@example.com"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        String userId = JsonPath.parse(responseContent).read("$.id");

        result = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testproduct3"))
                .andExpect(jsonPath("$.description").value("testproductdescription3"))
                .andExpect(jsonPath("$.price").value("1500.0"))
                .andExpect(jsonPath("$.quantity").value("12"))
                .andReturn();

        responseContent = result.getResponse().getContentAsString();
        String productId = JsonPath.parse(responseContent).read("$.id");

        String newOrderJson = String.format("{ \"userId\": \"%s\", \"productId\": \"%s\", \"quantity\": 5 }", userId, productId);

        result = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newOrderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.quantity").value("5"))
                .andReturn();

        responseContent = result.getResponse().getContentAsString();
        String orderId = JsonPath.parse(responseContent).read("$.id");

        mockMvc.perform(get("/orders")
                        .param("orderIDs", createdOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == '%s')]", createdOrderId).exists())
                .andExpect(jsonPath("$[?(@.id == '%s')]", orderId).exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Updating order with role ADMIN")
    void testUpdateOrder() throws Exception {
        if (StringUtils.isEmpty(createdOrderId)) {
            log.info("Creating order to be updated later");
            testPlaceOrder();
        }

        String updatedOrderJson = "{ \"id\": \"" + createdOrderId + "\", \"userId\": \"" + createdUserId + "\", \"productId\": \"" + createdProductId + "\", \"quantity\": \"3\"}";

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedOrderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Order has been successfully updated"));

        log.info("Order with ID {} has been successfully updated with quantity 3", createdOrderId);

        mockMvc.perform(get("/orders/" + createdOrderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdOrderId))
                .andExpect(jsonPath("$.userId").value(createdUserId))
                .andExpect(jsonPath("$.productId").value(createdProductId))
                .andExpect(jsonPath("$.quantity").value("3"))
                .andExpect(jsonPath("$.status").value("Pending"));
        log.info("Updated order has been successfully verified");
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Trying to delete an order with role USER")
    void testDeleteOrderFails() throws Exception {
        mockMvc.perform(delete("/orders/{id}", createdOrderId))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE)).andReturn();

        log.info("Deleting order with ID {} with role USER failed!", createdOrderId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Cancelling order with role ADMIN")
    void testCancelOrder() throws Exception {
        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        String userId = JsonPath.parse(responseContent).read("$.id");

        result = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testproduct"))
                .andExpect(jsonPath("$.description").value("testproductdescription"))
                .andExpect(jsonPath("$.price").value("500.0"))
                .andExpect(jsonPath("$.quantity").value("35"))
                .andReturn();

        responseContent = result.getResponse().getContentAsString();
        String productId = JsonPath.parse(responseContent).read("$.id");

        String newOrderJson = String.format("{ \"userId\": \"%s\", \"productId\": \"%s\", \"quantity\": 5 }", userId, productId);

        result = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newOrderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.quantity").value("5"))
                .andReturn();

        responseContent = result.getResponse().getContentAsString();
        String orderId = JsonPath.parse(responseContent).read("$.id");

        mockMvc.perform(delete("/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Order has been successfully deleted"));

        log.info("Order with ID {} has been cancelled ", orderId);

        Products productAfter = productsService.getProductById(UUID.fromString(productId)).orElseThrow(() -> new Exception("Product not found"));
        int updatedQuantity = productAfter.getQuantity();

        // Verify that the product quantity has been updated correctly
        assertEquals(35, updatedQuantity, "Product quantity should be updated correctly after order cancellation");
    }
}