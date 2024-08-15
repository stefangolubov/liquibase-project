package com.liquibaseproject.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NamedStoredProcedureQuery(
        name = "placeOrder",
        procedureName = "place_order",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = UUID.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_product_id", type = UUID.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_quantity", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_order_id", type = UUID.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_order_date", type = Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_status", type = String.class)
        }
)
public class Orders {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "status")
    private String status;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id + '\'' +
                "userId=" + userId + '\'' +
                "productId=" + productId + '\'' +
                "quantity=" + quantity + '\'' +
                "orderDate=" + orderDate + '\'' +
                "status=" + status + '\'' +
                '}';
    }
}