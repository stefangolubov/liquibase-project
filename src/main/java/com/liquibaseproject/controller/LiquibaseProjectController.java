package com.liquibaseproject.controller;

import com.liquibaseproject.entity.Products;
import com.liquibaseproject.exception.InvalidInputException;
import com.liquibaseproject.exception.ResultsNotFoundException;
import com.liquibaseproject.mapper.*;
import com.liquibaseproject.model.*;
import com.liquibaseproject.service.OrdersService;
import com.liquibaseproject.service.ProductsService;
import com.liquibaseproject.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

@RestController
@RequestMapping("/api")
public class LiquibaseProjectController implements UsersApiDelegate, ProductsApiDelegate, OrdersApiDelegate {

    private static final String USERS_NOT_FOUND_EXCEPTION_MESSAGE = "No users found for the provided input";
    private static final String USERS_SUCCESS_MESSAGE = "User has been successfully %s";
    private static final String USERNAME_AND_E_MAIL_ARE_MANDATORY = "Username and e-mail are mandatory";
    private static final String PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE = "No products found for the provided input";
    private static final String PRODUCTS_SUCCESS_MESSAGE = "Product has been successfully %s";
    private static final String NAME_PRICE_AND_QUANTITY_ARE_MANDATORY = "Name, price and quantity are mandatory";
    private static final String ORDERS_NOT_FOUND_EXCEPTION_MESSAGE = "No orders found for the provided input";
    private static final String ORDERS_SUCCESS_MESSAGE = "Order has been successfully %s";
    private static final String USER_ID_PRODUCT_ID_AND_QUANTITY_ARE_MANDATORY = "User ID, Product ID and quantity are mandatory";

    private static final String DELETED = "deleted";
    private static final String UPDATED = "updated";

    private final UsersService usersService;
    private final UsersMapper usersMapper;
    private final NewUserMapper newUserMapper;

    private final ProductsService productsService;
    private final ProductsMapper productsMapper;
    private final NewProductsMapper newProductsMapper;

    private final OrdersService ordersService;
    private final OrdersMapper ordersMapper;
    private final NewOrderMapper newOrderMapper;

    public LiquibaseProjectController(UsersService usersService, UsersMapper usersMapper, NewUserMapper newUserMapper,
                                      ProductsService productsService, ProductsMapper productsMapper, NewProductsMapper newProductsMapper,
                                      OrdersService ordersService, OrdersMapper ordersMapper, NewOrderMapper newOrderMapper) {
        this.usersService = usersService;
        this.usersMapper = usersMapper;
        this.newUserMapper = newUserMapper;
        this.productsService = productsService;
        this.productsMapper = productsMapper;
        this.newProductsMapper = newProductsMapper;
        this.ordersService = ordersService;
        this.ordersMapper = ordersMapper;
        this.newOrderMapper = newOrderMapper;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    //Users implementation

    @Override
    public ResponseEntity<ModelApiResponse> updateUser(Users usersModel) {
        checkMandatoryFields(usersModel.getUsername(), usersModel.getEmail());
        usersService.updateUser(usersModel.getId(), usersMapper.toEntity(usersModel));

        ModelApiResponse response = getApiResponseSchema(String.format(USERS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Users> addUser(NewUser usersModel) {
        checkMandatoryFields(usersModel.getUsername(), usersModel.getEmail());
        com.liquibaseproject.entity.Users entity = newUserMapper.toEntity(usersModel);
        com.liquibaseproject.entity.Users createdUser = usersService.addUser(entity);
        return ResponseEntity.ok(usersMapper.toModel(createdUser));
    }

    private static void checkMandatoryFields(String userName, String userEmail) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(userName) || org.apache.commons.lang3.StringUtils.isEmpty(userEmail)) {
            throw new InvalidInputException(USERNAME_AND_E_MAIL_ARE_MANDATORY);
        }
    }

    @Override
    public ResponseEntity<ModelApiResponse> deleteUser(UUID id) {
        usersService.deleteUser(id);

        ModelApiResponse response = getApiResponseSchema(String.format(USERS_SUCCESS_MESSAGE, DELETED));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<Users>> findUsersByUsername(String usernames) {
        String[] usernameList = usernames.split(",");
        Set<String> uniqueUsers = new LinkedHashSet<>(Arrays.asList(usernameList));
        List<com.liquibaseproject.entity.Users> userEntities = new ArrayList<>();

        for (String username : uniqueUsers) {
            userEntities.addAll(usersService.findByUsernameIgnoreCase(username.trim()));
        }

        if (userEntities.isEmpty()) {
            throw new ResultsNotFoundException(USERS_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        List<Users> users = userEntities.stream()
                .map(usersMapper::toModel)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<Users>> findAllUsers() {
        List<com.liquibaseproject.entity.Users> usersEntityList = usersService.findAll();
        List<Users> users = usersEntityList.stream()
                .map(usersMapper::toModel)
                .toList();

        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<Users> getUserById(UUID id) {
        Optional<com.liquibaseproject.entity.Users> userEntity = usersService.getUserById(id);
        return userEntity.map(usersMapper::toModel).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateUserWithForm(UUID id, String username, String email) {
        Optional<com.liquibaseproject.entity.Users> userEntityOptional = usersService.getUserById(id);

        userEntityOptional.ifPresent(userEntity -> {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(username)) {
                userEntity.setUsername(username);
            }
            if (StringUtils.isNotEmpty(email)) {
                userEntity.setEmail(email);
            }
            usersService.updateUser(id, userEntity);
        });

        ModelApiResponse response = getApiResponseSchema(String.format(USERS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    // Products implementation

    @Override
    public ResponseEntity<com.liquibaseproject.model.Products> addProduct(NewProduct newProduct) {
        checkMandatoryFields(newProduct.getName(), newProduct.getPrice(), newProduct.getQuantity());
        com.liquibaseproject.entity.Products entity = newProductsMapper.toEntity(newProduct);
        com.liquibaseproject.entity.Products createdProduct = productsService.addProduct(entity);
        return ResponseEntity.ok(productsMapper.toModel(createdProduct));
    }

    @Override
    public ResponseEntity<ModelApiResponse> deleteProduct(UUID id) {
        productsService.deleteProduct(id);

        ModelApiResponse response = getApiResponseSchema(String.format(PRODUCTS_SUCCESS_MESSAGE, DELETED));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<com.liquibaseproject.model.Products>> findAllProducts() {
        List<com.liquibaseproject.entity.Products> productsEntityList = productsService.findAll();
        List<com.liquibaseproject.model.Products> products = productsEntityList.stream()
                .map(productsMapper::toModel)
                .toList();

        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<List<com.liquibaseproject.model.Products>> findProductsByName(String names) {
        String[] productsList = names.split(",");
        Set<String> uniqueProducts = new LinkedHashSet<>(Arrays.asList(productsList));
        List<com.liquibaseproject.entity.Products> productEntities = new ArrayList<>();

        for (String name : uniqueProducts) {
            productEntities.addAll(productsService.findByNameIgnoreCase(name.trim()));
        }

        if (productEntities.isEmpty()) {
            throw new ResultsNotFoundException(String.format(PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE));
        }

        List<com.liquibaseproject.model.Products> products = productEntities.stream()
                .map(productsMapper::toModel)
                .toList();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<com.liquibaseproject.model.Products> getProductById(UUID id) {
        Optional<com.liquibaseproject.entity.Products> productsEntity = productsService.getProductById(id);
        return productsEntity.map(productsMapper::toModel).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateProduct(com.liquibaseproject.model.Products products) {
        checkMandatoryFields(products.getName(), products.getPrice(), products.getQuantity());
        productsService.updateProduct(products.getId(), productsMapper.toEntity(products));

        ModelApiResponse response = getApiResponseSchema(String.format(PRODUCTS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    private static void checkMandatoryFields(String productName, Double productPrice, Integer productQuantity) {
        if (StringUtils.isEmpty(productName) || Objects.isNull(productPrice) || Objects.isNull(productQuantity)) {
            throw new InvalidInputException(NAME_PRICE_AND_QUANTITY_ARE_MANDATORY);
        }
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateProductWithForm(UUID id, String name, String description, Double price, Integer quantity) {
        Optional<com.liquibaseproject.entity.Products> productsEntityOptional = productsService.getProductById(id);

        productsEntityOptional.ifPresent(productEntity -> {
            if (StringUtils.isNotEmpty(name)) {
                productEntity.setName(name);
            }
            if (StringUtils.isNotEmpty(description)) {
                productEntity.setDescription(description);
            }
            if (Objects.nonNull(price)) {
                productEntity.setPrice(price);
            }
            if (Objects.nonNull(quantity)) {
                productEntity.setQuantity(quantity);
            }

            productsService.updateProduct(id, productEntity);
        });

        ModelApiResponse response = getApiResponseSchema(String.format(PRODUCTS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    // Orders implementation

    @Override
    public ResponseEntity<Orders> addOrder(NewOrder newOrder) {
        checkMandatoryFields(newOrder.getUserId(), newOrder.getProductId(), newOrder.getQuantity());
        com.liquibaseproject.entity.Orders entity = newOrderMapper.toEntity(newOrder);
        com.liquibaseproject.entity.Orders createdProduct = ordersService.addOrder(entity);
        return ResponseEntity.ok(ordersMapper.toModel(createdProduct));
    }

    @Override
    @Transactional
    public ResponseEntity<ModelApiResponse> deleteOrder(UUID id) {
        resetProductQuantityValue(id);

        ordersService.deleteOrder(id);
        ModelApiResponse response = getApiResponseSchema(String.format(ORDERS_SUCCESS_MESSAGE, DELETED));

        return ResponseEntity.ok(response);
    }

    private void resetProductQuantityValue(UUID id) {
        Optional<com.liquibaseproject.entity.Orders> order = ordersService.getOrderById(id);
        if (order.isPresent()) {
            Optional<Products> product = productsService.getProductById(order.get().getProductId());
            if (product.isPresent()) {
                product.get().setQuantity(product.get().getQuantity() + order.get().getQuantity());
                productsService.addProduct(product.get());
            }
        }
    }

    @Override
    public ResponseEntity<List<Orders>> findAllOrders() {
        List<com.liquibaseproject.entity.Orders> ordersEntityList = ordersService.findAll();
        List<Orders> orders = ordersEntityList.stream()
                .map(ordersMapper::toModel)
                .toList();

        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<List<Orders>> findOrdersById(String orderIDs) {
        String[] ordersList = orderIDs.split(",");
        Set<String> uniqueOrders = new LinkedHashSet<>(Arrays.asList(ordersList));
        List<com.liquibaseproject.entity.Orders> orderEntities = new ArrayList<>();

        for (String orderId : uniqueOrders) {
            ordersService.getOrderById(UUID.fromString(orderId.trim())).ifPresent(orderEntities::add);
        }

        if (orderEntities.isEmpty()) {
            throw new ResultsNotFoundException(String.format(ORDERS_NOT_FOUND_EXCEPTION_MESSAGE));
        }

        List<Orders> orders = orderEntities.stream()
                .map(ordersMapper::toModel)
                .toList();

        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<Orders> getOrderById(UUID id) {
        Optional<com.liquibaseproject.entity.Orders> ordersEntity = ordersService.getOrderById(id);
        return ordersEntity.map(ordersMapper::toModel).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateOrder(Orders orderModel) {
        checkMandatoryFields(orderModel.getUserId(), orderModel.getProductId(), orderModel.getQuantity());
        ordersService.updateOrder(orderModel.getId(), ordersMapper.toEntity(orderModel));

        ModelApiResponse response = getApiResponseSchema(String.format(ORDERS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    private static void checkMandatoryFields(UUID userId, UUID productId, Integer quantity) {
        if (Objects.isNull(userId) || Objects.isNull(productId) || Objects.isNull(quantity)) {
            throw new InvalidInputException(USER_ID_PRODUCT_ID_AND_QUANTITY_ARE_MANDATORY);
        }
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateOrderWithForm(UUID id, Integer quantity, String status) {
        Optional<com.liquibaseproject.entity.Orders> orderEntityOptional = ordersService.getOrderById(id);

        orderEntityOptional.ifPresent(orderEntity -> {
            if (Objects.nonNull(quantity)) {
                orderEntity.setQuantity(quantity);
            }
            if (io.micrometer.common.util.StringUtils.isNotEmpty(status)) {
                orderEntity.setStatus(status);
            }

            ordersService.updateOrder(id, orderEntity);
        });

        ModelApiResponse response = getApiResponseSchema(String.format(ORDERS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    private static ModelApiResponse getApiResponseSchema(String message) {
        ModelApiResponse response = new ModelApiResponse();

        response.setCode(200);
        response.setType("success");
        response.setMessage(message);
        return response;
    }
}