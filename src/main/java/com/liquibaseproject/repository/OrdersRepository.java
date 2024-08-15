package com.liquibaseproject.repository;

import com.liquibaseproject.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;

import java.sql.Timestamp;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID>, JpaSpecificationExecutor<Orders> {

    @Procedure(name = "placeOrder")
    void placeOrder(UUID p_user_id, UUID p_product_id, Integer p_quantity, UUID p_order_id, Timestamp p_order_date, String p_status);
}