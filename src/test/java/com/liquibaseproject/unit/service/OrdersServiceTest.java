package com.liquibaseproject.unit.service;

import com.liquibaseproject.entity.Orders;
import com.liquibaseproject.exception.ServiceProcessingException;
import com.liquibaseproject.repository.OrdersRepository;
import com.liquibaseproject.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.liquibaseproject.constant.ExceptionConstants.ORDERS_NOT_FOUND_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdersServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrdersService ordersService;

    private Orders order;
    private UUID orderId;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Orders();
        order.setId(orderId);
        order.setUserId(UUID.randomUUID());
        order.setProductId(UUID.randomUUID());
        order.setQuantity(10);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setStatus("PENDING");
    }

    @Test
    void testGetOrderById_OrderExists() {
        when(ordersRepository.existsById(orderId)).thenReturn(true);
        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Orders> result = ordersService.getOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    void testGetOrderById_OrderDoesNotExist() {
        when(ordersRepository.existsById(orderId)).thenReturn(false);

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> ordersService.getOrderById(orderId));

        assertEquals(ORDERS_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testFindAll() {
        when(ordersRepository.findAll()).thenReturn(Collections.singletonList(order));

        List<Orders> result = ordersService.findAll();

        assertEquals(1, result.size());
        assertEquals(order, result.getFirst());
    }
//
//    @Test
//    void testAddOrder() {
//        //doNothing().when(ordersRepository).placeOrder(any(), any(), anyInt(), any(), any(), any());
//
//        Orders result = ordersService.addOrder(order);
//
//        verify(ordersRepository).placeOrder(
//                eq(order.getUserId()),
//                eq(order.getProductId()),
//                eq(order.getQuantity()),
//                any(UUID.class),
//                any(Timestamp.class),
//                eq(order.getStatus())
//        );
//
//        assertEquals(order, result);
//    }

    @Test
    void testAddOrder() {
        UUID generatedId = UUID.randomUUID();
        Orders createdOrder = new Orders();
        createdOrder.setId(generatedId);
        createdOrder.setUserId(order.getUserId());
        createdOrder.setProductId(order.getProductId());
        createdOrder.setQuantity(order.getQuantity());
        createdOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
        createdOrder.setStatus("PENDING");

// Mock the repository methods
        when(ordersRepository.placeOrder(any(UUID.class), any(UUID.class), anyInt(), any(UUID.class), any(Timestamp.class), anyString()))
                .thenReturn(generatedId);
        when(ordersRepository.findById(generatedId)).thenReturn(Optional.of(createdOrder));

        Orders result = ordersService.addOrder(order);

// Verify the interactions with the repository
        verify(ordersRepository).placeOrder(
                eq(order.getUserId()),
                eq(order.getProductId()),
                eq(order.getQuantity()),
                any(UUID.class),
                any(Timestamp.class),
                eq(order.getStatus())
        );

// Assert the result
        assertNotNull(result.getId());
        assertEquals(generatedId, result.getId());
        assertEquals(createdOrder.getOrderDate(), result.getOrderDate());
        assertEquals(createdOrder.getStatus(), result.getStatus());
    }

    @Test
    void testUpdateOrder_OrderExists() {
        Orders updatedOrder = new Orders();
        updatedOrder.setQuantity(10);
        updatedOrder.setStatus("Shipped");

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));

        ordersService.updateOrder(orderId, updatedOrder);

        verify(ordersRepository).save(order);
        assertEquals(10, order.getQuantity());
        assertEquals("Shipped", order.getStatus());
    }

    @Test
    void testUpdateOrder_OrderDoesNotExist() {
        Orders updatedOrder = new Orders();
        updatedOrder.setQuantity(10);
        updatedOrder.setStatus("Shipped");

        when(ordersRepository.findById(orderId)).thenReturn(Optional.empty());

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> ordersService.updateOrder(orderId, updatedOrder));

        assertEquals(ORDERS_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testDeleteOrder_OrderExists() {
        when(ordersRepository.existsById(orderId)).thenReturn(true);

        ordersService.deleteOrder(orderId);

        verify(ordersRepository).deleteById(orderId);
    }

    @Test
    void testDeleteOrder_OrderDoesNotExist() {
        when(ordersRepository.existsById(orderId)).thenReturn(false);

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> ordersService.deleteOrder(orderId));

        assertEquals(ORDERS_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }
}