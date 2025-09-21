package com.quicken.orders.order_processing.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany
    private List<Product> productId = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, PROCESSING, COMPLETED, FAILED
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public List<Product> getProductId() {
        return productId;
    }

    public void setProductId(List<Product> productId) {
        this.productId = productId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", status=" + status +
                '}';
    }
}
