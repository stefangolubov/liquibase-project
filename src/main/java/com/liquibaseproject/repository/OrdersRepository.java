package com.liquibaseproject.repository;

import com.liquibaseproject.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID>, JpaSpecificationExecutor<Orders> {

    @Query(value = "CALL place_order(:userId, :productId, :quantity, :orderId, :orderDate, :status)", nativeQuery = true)
    UUID placeOrder(@Param("userId") UUID userId,
                    @Param("productId") UUID productId,
                    @Param("quantity") Integer quantity,
                    @Param("orderId") UUID orderId,
                    @Param("orderDate") Timestamp orderDate,
                    @Param("status") String status);
}