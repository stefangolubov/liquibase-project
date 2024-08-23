package com.liquibaseproject.api;

import com.liquibaseproject.entity.Products;
import com.liquibaseproject.exception.InvalidInputException;
import com.liquibaseproject.exception.ResultsNotFoundException;
import com.liquibaseproject.mapper.NewOrderMapper;
import com.liquibaseproject.mapper.OrdersMapper;
import com.liquibaseproject.model.ApiResponseSchema;
import com.liquibaseproject.model.NewOrder;
import com.liquibaseproject.model.Orders;
import com.liquibaseproject.service.OrdersService;
import com.liquibaseproject.service.ProductsService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrdersApiDelegateImpl implements OrdersApiDelegate {

    private static final String ORDERS_NOT_FOUND_EXCEPTION_MESSAGE = "No orders found for the provided input";
    private static final String SUCCESS_MESSAGE = "Order has been successfully %s";
    public static final String USER_ID_PRODUCT_ID_AND_QUANTITY_ARE_MANDATORY = "User ID, Product ID and quantity are mandatory";

    private final OrdersMapper ordersMapper;
    private final NewOrderMapper newOrderMapper;
    private final OrdersService ordersService;
    private final ProductsService productsService;

    public OrdersApiDelegateImpl(OrdersMapper ordersMapper, NewOrderMapper newOrderMapper, OrdersService ordersService, ProductsService productsService) {
        this.ordersMapper = ordersMapper;
        this.newOrderMapper = newOrderMapper;
        this.ordersService = ordersService;
        this.productsService = productsService;
    }

    @Override
    public ResponseEntity<Orders> addOrder(NewOrder newOrder) {
        checkMandatoryFields(newOrder.getUserId(), newOrder.getProductId(), newOrder.getQuantity());
        com.liquibaseproject.entity.Orders entity = newOrderMapper.toEntity(newOrder);
        com.liquibaseproject.entity.Orders createdProduct = ordersService.addOrder(entity);
        return ResponseEntity.ok(ordersMapper.toModel(createdProduct));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseSchema> deleteOrder(UUID id) {
        resetProductQuantityValue(id);

        ordersService.deleteOrder(id);
        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "deleted"));

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
    public ResponseEntity<ApiResponseSchema> updateOrder(Orders orderModel) {
        checkMandatoryFields(orderModel.getUserId(), orderModel.getProductId(), orderModel.getQuantity());
        ordersService.updateOrder(orderModel.getId(), ordersMapper.toEntity(orderModel));

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "updated"));

        return ResponseEntity.ok(response);
    }

    private static void checkMandatoryFields(UUID userId, UUID productId, Integer quantity) {
        if (Objects.isNull(userId) || Objects.isNull(productId) || Objects.isNull(quantity)) {
            throw new InvalidInputException(USER_ID_PRODUCT_ID_AND_QUANTITY_ARE_MANDATORY);
        }
    }

    @Override
    public ResponseEntity<ApiResponseSchema> updateOrderWithForm(UUID id, Integer quantity, String status) {
        Optional<com.liquibaseproject.entity.Orders> orderEntityOptional = ordersService.getOrderById(id);

        orderEntityOptional.ifPresent(orderEntity -> {
            if (Objects.nonNull(quantity)) {
                orderEntity.setQuantity(quantity);
            }
            if (StringUtils.isNotEmpty(status)) {
                orderEntity.setStatus(status);
            }

            ordersService.updateOrder(id, orderEntity);
        });

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "updated"));

        return ResponseEntity.ok(response);
    }

    private static ApiResponseSchema getApiResponseSchema(String message) {
        ApiResponseSchema response = new ApiResponseSchema();

        response.setCode(200);
        response.setType("success");
        response.setMessage(message);
        return response;
    }
}