package com.quicken.orders.order_processing.repository;

import com.quicken.orders.order_processing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
