package com.quicken.orders.order_processing.dto;

import com.quicken.orders.order_processing.entity.Order;
import com.quicken.orders.order_processing.entity.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    @NotNull
    @NotEmpty
    @Size
    private List<Product> productId=new ArrayList<>();

    private Order.Status status;

    public Order.Status getStatus() {
        return status;
    }

    public void setStatus(Order.Status status) {
        this.status = status;
    }

    public List<Product> getProductId() {
        return productId;
    }

    public void setProductId(List<Product> productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "productId=" + productId +
                ", status=" + status +
                '}';
    }
}
