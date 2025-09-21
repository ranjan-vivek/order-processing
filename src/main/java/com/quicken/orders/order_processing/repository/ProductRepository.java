package com.quicken.orders.order_processing.repository;

import com.quicken.orders.order_processing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
