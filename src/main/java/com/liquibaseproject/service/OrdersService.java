package com.liquibaseproject.service;

import com.liquibaseproject.entity.Orders;
import com.liquibaseproject.repository.OrdersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Optional<Orders> getOrderById(UUID id) {
        return ordersRepository.findById(id);
    }

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Orders addOrder(Orders order) {
        UUID id = UUID.randomUUID();
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());

        ordersRepository.placeOrder(order.getUserId(), order.getProductId(), order.getQuantity(), id, orderDate, order.getStatus());
        return order;
    }

    public void updateOrder(UUID id, Orders ordersEntity) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (Objects.nonNull(ordersEntity.getQuantity())) {
            order.setQuantity(ordersEntity.getQuantity());
        }

        if (StringUtils.isNotEmpty(ordersEntity.getStatus())) {
            order.setStatus(ordersEntity.getStatus());
        }

        ordersRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        ordersRepository.deleteById(id);
    }
}