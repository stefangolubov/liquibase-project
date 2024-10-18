package com.liquibaseproject.unit.controller;

import com.liquibaseproject.controller.LiquibaseProjectController;
import com.liquibaseproject.entity.Orders;
import com.liquibaseproject.exception.ServiceProcessingException;
import com.liquibaseproject.mapper.*;
import com.liquibaseproject.model.*;
import com.liquibaseproject.service.OrdersService;
import com.liquibaseproject.service.ProductsService;
import com.liquibaseproject.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.liquibaseproject.constant.ExceptionConstants.NAME_PRICE_AND_QUANTITY_ARE_MANDATORY;
import static com.liquibaseproject.constant.ExceptionConstants.USERNAME_AND_E_MAIL_ARE_MANDATORY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LiquibaseProjectControllerUnitTest {

    @InjectMocks
    private LiquibaseProjectController liquibaseProjectController;

    @Mock
    private UsersService usersService;

    @Mock
    private UsersMapper usersMapper;

    @Mock
    private NewUserMapper newUserMapper;

    @Mock
    private ProductsService productsService;

    @Mock
    private ProductsMapper productsMapper;

    @Mock
    private NewProductsMapper newProductsMapper;

    @Mock
    private OrdersMapper ordersMapper;

    @Mock
    private NewOrderMapper newOrderMapper;

    @Mock
    private OrdersService ordersService;

    private com.liquibaseproject.entity.Users userEntity;
    private com.liquibaseproject.model.Users userModel;
    private NewUser newUser;
    private UUID userId;

    private com.liquibaseproject.entity.Products productEntity;
    private com.liquibaseproject.model.Products productModel;
    private NewProduct newProduct;
    private UUID productId;

    private com.liquibaseproject.model.Orders orderModel;
    private Orders orderEntity;
    private NewOrder newOrder;

    @BeforeEach
    void setUp() {
        setUpUsers();
        setUpProducts();
        setUpOrders();
    }

    private void setUpUsers() {
        userId = UUID.randomUUID();
        userEntity = new com.liquibaseproject.entity.Users();
        userEntity.setId(userId);
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@example.com");

        userModel = new Users();
        userModel.setId(userId);
        userModel.setUsername("testuser");
        userModel.setEmail("testuser@example.com");

        newUser = new NewUser();
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");
    }

    private void setUpProducts() {
        productId = UUID.randomUUID();
        productEntity = new com.liquibaseproject.entity.Products();
        productEntity.setId(productId);
        productEntity.setName("Test Product");
        productEntity.setDescription("Test Description");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(10);

        productModel = new com.liquibaseproject.model.Products();
        productModel.setId(productId);
        productModel.setName("Test Product");
        productModel.setDescription("Test Description");
        productModel.setPrice(100.0);
        productModel.setQuantity(10);

        newProduct = new NewProduct();
        newProduct.setName("New Product");
        newProduct.setPrice(150.0);
        newProduct.setQuantity(20);
    }

    private void setUpOrders() {
        orderModel = new com.liquibaseproject.model.Orders();
        orderModel.setId(UUID.randomUUID());
        orderModel.setUserId(UUID.randomUUID());
        orderModel.setProductId(UUID.randomUUID());
        orderModel.setQuantity(1);

        orderEntity = new Orders();
        orderEntity.setId(orderModel.getId());
        orderEntity.setUserId(orderModel.getUserId());
        orderEntity.setProductId(orderModel.getProductId());
        orderEntity.setQuantity(orderModel.getQuantity());

        newOrder = new NewOrder();
        newOrder.setUserId(orderModel.getUserId());
        newOrder.setProductId(orderModel.getProductId());
        newOrder.setQuantity(orderModel.getQuantity());
    }

    // Users

    @Test
    @DisplayName("Updating user")
    void testUpdateUser() {
        when(usersMapper.toEntity(any(com.liquibaseproject.model.Users.class))).thenReturn(userEntity);
        doNothing().when(usersService).updateUser(any(UUID.class), any(com.liquibaseproject.entity.Users.class));

        ResponseEntity<ModelApiResponse> response = liquibaseProjectController.updateUser(userModel);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("success", response.getBody().getType());
        assertEquals("User has been successfully updated", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Adding user")
    void testAddUser() {
        when(newUserMapper.toEntity(any(NewUser.class))).thenReturn(userEntity);
        when(usersService.addUser(any(com.liquibaseproject.entity.Users.class))).thenReturn(userEntity);
        when(usersMapper.toModel(any(com.liquibaseproject.entity.Users.class))).thenReturn(userModel);

        ResponseEntity<com.liquibaseproject.model.Users> response = liquibaseProjectController.addUser(newUser);

        assertEquals(userModel, response.getBody());
    }

    @Test
    @DisplayName("Deleting user")
    void testDeleteUser() {
        doNothing().when(usersService).deleteUser(any(UUID.class));

        ResponseEntity<ModelApiResponse> response = liquibaseProjectController.deleteUser(userId);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("success", response.getBody().getType());
        assertEquals("User has been successfully deleted", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Find users by usernames")
    void testFindUsersByUsernames() {
        when(usersService.findByUsernameIgnoreCase(anyString())).thenReturn(Collections.singletonList(userEntity));
        when(usersMapper.toModel(any(com.liquibaseproject.entity.Users.class))).thenReturn(userModel);

        ResponseEntity<List<Users>> response = liquibaseProjectController.findUsersByUsername("testuser");

        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(userModel, response.getBody().getFirst());
    }

    @Test
    @DisplayName("Find all users")
    void testFindAllUsers() {
        when(usersService.findAll()).thenReturn(Collections.singletonList(userEntity));
        when(usersMapper.toModel(any(com.liquibaseproject.entity.Users.class))).thenReturn(userModel);

        ResponseEntity<List<com.liquibaseproject.model.Users>> response = liquibaseProjectController.findAllUsers();

        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(userModel, response.getBody().getFirst());
    }

    @Test
    @DisplayName("Find user by ID")
    void testGetUserById() {
        when(usersService.getUserById(any(UUID.class))).thenReturn(Optional.of(userEntity));
        when(usersMapper.toModel(any(com.liquibaseproject.entity.Users.class))).thenReturn(userModel);

        ResponseEntity<com.liquibaseproject.model.Users> response = liquibaseProjectController.getUserById(userId);

        assertEquals(userModel, response.getBody());
    }

    @Test
    @DisplayName("Update user with form")
    void testUpdateUserWithForm() {
        when(usersService.getUserById(any(UUID.class))).thenReturn(Optional.of(userEntity));
        doNothing().when(usersService).updateUser(any(UUID.class), any(com.liquibaseproject.entity.Users.class));

        ResponseEntity<ModelApiResponse> response = liquibaseProjectController.updateUserWithForm(userId, "updateduser", "updateduser@example.com");

        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("success", response.getBody().getType());
        assertEquals("User has been successfully updated", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Throwing exception when checking mandatory fields for adding a user")
    void testCheckMandatoryFields_Users_ThrowsException() {
        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> LiquibaseProjectController.checkMandatoryFields("", ""));

        assertEquals(USERNAME_AND_E_MAIL_ARE_MANDATORY, exception.getMessage());
    }

    // Products

    @Test
    @DisplayName("Add product")
    void testAddProduct() {
        when(newProductsMapper.toEntity(any(NewProduct.class))).thenReturn(productEntity);
        when(productsService.addProduct(any(com.liquibaseproject.entity.Products.class))).thenReturn(productEntity);
        when(productsMapper.toModel(any(com.liquibaseproject.entity.Products.class))).thenReturn(productModel);

        ResponseEntity<com.liquibaseproject.model.Products> response = liquibaseProjectController.addProduct(newProduct);

        assertEquals(productModel, response.getBody());
    }

    @Test
    @DisplayName("Delete product")
    void testDeleteProduct() {
        doNothing().when(productsService).deleteProduct(any(UUID.class));

        ResponseEntity<ModelApiResponse> response = liquibaseProjectController.deleteProduct(productId);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("success", response.getBody().getType());
        assertEquals("Product has been successfully deleted", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Find all products")
    void testFindAllProducts() {
        when(productsService.findAll()).thenReturn(Collections.singletonList(productEntity));
        when(productsMapper.toModel(any(com.liquibaseproject.entity.Products.class))).thenReturn(productModel);

        ResponseEntity<List<com.liquibaseproject.model.Products>> response = liquibaseProjectController.findAllProducts();

        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(productModel, response.getBody().getFirst());
    }

    @Test
    @DisplayName("Find products by names")
    void testFindProductsByNames() {
        when(productsService.findByNameIgnoreCase(anyString())).thenReturn(Collections.singletonList(productEntity));
        when(productsMapper.toModel(any(com.liquibaseproject.entity.Products.class))).thenReturn(productModel);

        ResponseEntity<List<com.liquibaseproject.model.Products>> response = liquibaseProjectController.findProductsByName("Test Product");

        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(productModel, response.getBody().getFirst());
    }

    @Test
    @DisplayName("Get order by ID")
    void testGetProductById() {
        when(productsService.getProductById(any(UUID.class))).thenReturn(Optional.of(productEntity));
        when(productsMapper.toModel(any(com.liquibaseproject.entity.Products.class))).thenReturn(productModel);

        ResponseEntity<com.liquibaseproject.model.Products> response = liquibaseProjectController.getProductById(productId);

        assertEquals(productModel, response.getBody());
    }

    @Test
    @DisplayName("Update product")
    void testUpdateProduct() {
        when(productsMapper.toEntity(any(com.liquibaseproject.model.Products.class))).thenReturn(productEntity);
        doNothing().when(productsService).updateProduct(any(UUID.class), any(com.liquibaseproject.entity.Products.class));

        ResponseEntity<ModelApiResponse> response = liquibaseProjectController.updateProduct(productModel);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("success", response.getBody().getType());
        assertEquals("Product has been successfully updated", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Update product with form")
    void testUpdateProductWithForm() {
        when(productsService.getProductById(any(UUID.class))).thenReturn(Optional.of(productEntity));
        doNothing().when(productsService).updateProduct(any(UUID.class), any(com.liquibaseproject.entity.Products.class));

        ResponseEntity<ModelApiResponse> response =
                liquibaseProjectController.updateProductWithForm(productId, "Updated Product", "Updated Description", 200.0, 30);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("success", response.getBody().getType());
        assertEquals("Product has been successfully updated", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Throwing exception when checking mandatory fields for adding a product")
    void testCheckMandatoryFields_Products_ThrowsException() {
        ServiceProcessingException exception = assertThrows(
                ServiceProcessingException.class, () -> LiquibaseProjectController.checkMandatoryFields("", null, null));

        assertEquals(NAME_PRICE_AND_QUANTITY_ARE_MANDATORY, exception.getMessage());
    }

    // Orders

    @Test
    @DisplayName("Add order")
    void testAddOrder() {
        when(newOrderMapper.toEntity(any(NewOrder.class))).thenReturn(orderEntity);
        when(ordersService.addOrder(any(Orders.class))).thenReturn(orderEntity);
        when(ordersMapper.toModel(any(Orders.class))).thenReturn(orderModel);

        ResponseEntity<com.liquibaseproject.model.Orders> response = liquibaseProjectController.addOrder(newOrder);

        verify(ordersService, times(1)).addOrder(any(Orders.class));
        verify(ordersMapper, times(1)).toModel(any(Orders.class));
        assertEquals(orderModel, response.getBody());
    }

    @Test
    @DisplayName("Delete order")
    void testDeleteOrder() {
        UUID orderId = UUID.randomUUID();
        doNothing().when(ordersService).deleteOrder(orderId);

        ResponseEntity<ModelApiResponse> response = liquibaseProjectController.deleteOrder(orderId);

        verify(ordersService, times(1)).deleteOrder(orderId);
        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("Order has been successfully deleted", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Find all orders")
    void testFindAllOrders() {
        List<Orders> ordersList = Collections.singletonList(orderEntity);
        when(ordersService.findAll()).thenReturn(ordersList);
        when(ordersMapper.toModel(any(Orders.class))).thenReturn(orderModel);

        ResponseEntity<List<com.liquibaseproject.model.Orders>> response = liquibaseProjectController.findAllOrders();

        verify(ordersService, times(1)).findAll();
        verify(ordersMapper, times(1)).toModel(any(Orders.class));
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(orderModel, response.getBody().getFirst());
    }

    @Test
    @DisplayName("Get order by ID")
    void testGetOrderById() {
        UUID orderId = UUID.randomUUID();
        when(ordersService.getOrderById(orderId)).thenReturn(Optional.of(orderEntity));
        when(ordersMapper.toModel(any(Orders.class))).thenReturn(orderModel);

        ResponseEntity<com.liquibaseproject.model.Orders> response = liquibaseProjectController.getOrderById(orderId);

        verify(ordersService, times(1)).getOrderById(orderId);
        verify(ordersMapper, times(1)).toModel(any(Orders.class));
        assertEquals(orderModel, response.getBody());
    }

    @Test
    @DisplayName("Update order")
    void testUpdateOrder() {
        when(ordersMapper.toEntity(any(com.liquibaseproject.model.Orders.class))).thenReturn(orderEntity);
        doNothing().when(ordersService).updateOrder(any(UUID.class), any(Orders.class));

        ResponseEntity<ModelApiResponse> response = liquibaseProjectController.updateOrder(orderModel);

        verify(ordersService, times(1)).updateOrder(any(UUID.class), any(Orders.class));
        assertEquals(200, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("Order has been successfully updated", response.getBody().getMessage());
    }
}