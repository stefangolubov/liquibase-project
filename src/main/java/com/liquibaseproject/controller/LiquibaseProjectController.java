package com.liquibaseproject.controller;

import com.liquibaseproject.entity.Products;
import com.liquibaseproject.exception.ExceptionUtil;
import com.liquibaseproject.exception.LiquibaseProjectException;
import com.liquibaseproject.mapper.*;
import com.liquibaseproject.model.*;
import com.liquibaseproject.service.OrdersService;
import com.liquibaseproject.service.ProductsService;
import com.liquibaseproject.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LiquibaseProjectController implements UsersApiDelegate, ProductsApiDelegate, OrdersApiDelegate {

    private static final String USERS_SUCCESS_MESSAGE = "User has been successfully %s";
    private static final String PRODUCTS_SUCCESS_MESSAGE = "Product has been successfully %s";
    private static final String ORDERS_SUCCESS_MESSAGE = "Order has been successfully %s";

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

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    //Users implementation

    @Override
    public ResponseEntity<ModelApiResponse> updateUser(Users usersModel) {
        checkMandatoryFields(usersModel.getUsername(), usersModel.getEmail());
        usersService.updateUser(usersModel.getId(), usersMapper.toEntity(usersModel));

        ModelApiResponse response = getModelApiResponseSchema(String.format(USERS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Users> addUser(NewUser usersModel) {
        checkMandatoryFields(usersModel.getUsername(), usersModel.getEmail());
        com.liquibaseproject.entity.Users entity = newUserMapper.toEntity(usersModel);
        com.liquibaseproject.entity.Users createdUser = usersService.addUser(entity);
        return ResponseEntity.ok(usersMapper.toModel(createdUser));
    }

    public static void checkMandatoryFields(String userName, String userEmail) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(userName) || org.apache.commons.lang3.StringUtils.isEmpty(userEmail)) {
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.MANDATORY_USERNAME_AND_E_MAIL);
        }
    }

    @Override
    public ResponseEntity<ModelApiResponse> deleteUser(UUID id) {
        try {
            usersService.deleteUser(id);

            ModelApiResponse response = getModelApiResponseSchema(String.format(USERS_SUCCESS_MESSAGE, DELETED));

            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.DATA_INTEGRITY_VIOLATION_USERS);
        }
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
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.USERS_NOT_FOUND_EXCEPTION);
        }

        List<Users> users = userEntities.stream()
                .map(usersMapper::toModel)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Override
    @GetMapping("/users")
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

        ModelApiResponse response = getModelApiResponseSchema(String.format(USERS_SUCCESS_MESSAGE, UPDATED));

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
        try {
            productsService.deleteProduct(id);

            ModelApiResponse response = getModelApiResponseSchema(String.format(PRODUCTS_SUCCESS_MESSAGE, DELETED));
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.DATA_INTEGRITY_VIOLATION_PRODUCTS);
        }
    }

    @Override
    @GetMapping("/products")
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
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.PRODUCTS_NOT_FOUND_EXCEPTION);
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

        ModelApiResponse response = getModelApiResponseSchema(String.format(PRODUCTS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    public static void checkMandatoryFields(String productName, Double productPrice, Integer productQuantity) {
        if (StringUtils.isEmpty(productName) || Objects.isNull(productPrice) || Objects.isNull(productQuantity)) {
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.MANDATORY_NAME_PRICE_AND_QUANTITY);
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

        ModelApiResponse response = getModelApiResponseSchema(String.format(PRODUCTS_SUCCESS_MESSAGE, UPDATED));

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
        ModelApiResponse response = getModelApiResponseSchema(String.format(ORDERS_SUCCESS_MESSAGE, DELETED));

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
    @GetMapping("/orders")
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
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.ORDERS_NOT_FOUND_EXCEPTION);
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

        ModelApiResponse response = getModelApiResponseSchema(String.format(ORDERS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    private static void checkMandatoryFields(UUID userId, UUID productId, Integer quantity) {
        if (Objects.isNull(userId) || Objects.isNull(productId) || Objects.isNull(quantity)) {
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.MANDATORY_USERID_PRODUCT_ID_AND_QUANTITY);
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

        ModelApiResponse response = getModelApiResponseSchema(String.format(ORDERS_SUCCESS_MESSAGE, UPDATED));

        return ResponseEntity.ok(response);
    }

    private static ModelApiResponse getModelApiResponseSchema(String message) {
        ModelApiResponse response = new ModelApiResponse();

        response.setCode(200);
        response.setType("success");
        response.setMessage(message);
        return response;
    }
}