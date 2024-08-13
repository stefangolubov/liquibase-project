package com.liquibaseproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "status")
    private String status;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
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
