package com.liquibaseproject.api;

import com.liquibaseproject.entity.Products;
import com.liquibaseproject.mapper.NewOrderMapper;
import com.liquibaseproject.mapper.OrdersMapper;
import com.liquibaseproject.model.ModelApiResponse;
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
        com.liquibaseproject.entity.Orders entity = newOrderMapper.toEntity(newOrder);
        com.liquibaseproject.entity.Orders createdProduct = ordersService.addOrder(entity);
        return ResponseEntity.ok(ordersMapper.toModel(createdProduct));
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteOrder(UUID id) {
        resetProductQuantityValue(id);

        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
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
        if (StringUtils.isEmpty(orderIDs)) {
            return ResponseEntity.badRequest().build();
        }

        String[] ordersList = orderIDs.split(",");
        Set<String> uniqueOrders = new LinkedHashSet<>(Arrays.asList(ordersList));
        List<com.liquibaseproject.entity.Orders> orderEntities = new ArrayList<>();

        for (String orderId : uniqueOrders) {
            ordersService.getOrderById(UUID.fromString(orderId.trim())).ifPresent(orderEntities::add);
        }

        if (orderEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
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
        ordersService.updateOrder(orderModel.getId(), ordersMapper.toEntity(orderModel));

        ModelApiResponse response = new ModelApiResponse();
        response.setCode(200);
        response.setType("success");
        response.setMessage("Order has been successfully updated");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> updateOrderWithForm(UUID id, Integer quantity, String status) {
        if (id == null || (quantity == null && StringUtils.isEmpty(status))) {
            return ResponseEntity.badRequest().build();
        }

        Optional<com.liquibaseproject.entity.Orders> orderEntityOptional = ordersService.getOrderById(id);
        if (orderEntityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        com.liquibaseproject.entity.Orders orderEntity = orderEntityOptional.get();
        if (Objects.nonNull(quantity)) {
            orderEntity.setQuantity(quantity);
        }
        if (StringUtils.isNotEmpty(status)) {
            orderEntity.setStatus(status);
        }

        ordersService.updateOrder(id, orderEntity);

        return ResponseEntity.noContent().build();
    }
}