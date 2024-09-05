package com.liquibaseproject.service;

import com.liquibaseproject.entity.Orders;
import com.liquibaseproject.exception.ResultsNotFoundException;
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

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order with ID %s does not exist.";

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Optional<Orders> getOrderById(UUID id) {
        if (ordersRepository.existsById(id)) {
            return ordersRepository.findById(id);
        } else {
            throw new ResultsNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, id));
        }
    }

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Orders addOrder(Orders order) {
        UUID generatedId = ordersRepository.placeOrder(order.getUserId(), order.getProductId(), order.getQuantity(), order.getId(), (Timestamp) order.getOrderDate(), order.getStatus());
        Orders createdOrder = ordersRepository.findById(generatedId).orElseThrow();

        order.setId(generatedId);
        order.setOrderDate(createdOrder.getOrderDate());
        order.setStatus(createdOrder.getStatus());

        return order;
    }

    public void updateOrder(UUID id, Orders ordersEntity) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new ResultsNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, id)));

        if (Objects.nonNull(ordersEntity.getQuantity())) {
            order.setQuantity(ordersEntity.getQuantity());
        }

        if (StringUtils.isNotEmpty(ordersEntity.getStatus())) {
            order.setStatus(ordersEntity.getStatus());
        }

        ordersRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        if (ordersRepository.existsById(id)) {
            ordersRepository.deleteById(id);
        } else {
            throw new ResultsNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, id));
        }
    }
}