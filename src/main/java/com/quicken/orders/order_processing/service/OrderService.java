package com.quicken.orders.order_processing.service;

import com.quicken.orders.order_processing.dto.OrderDTO;
import com.quicken.orders.order_processing.entity.Order;
import com.quicken.orders.order_processing.entity.Product;
import com.quicken.orders.order_processing.exception.NotFoundException;
import com.quicken.orders.order_processing.repository.OrderRepository;
import com.quicken.orders.order_processing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProcessingService orderProcessingService;

    public Order createOrder(OrderDTO orderDTO) {

        Order order = new Order();
        order.setProductId(orderDTO.getProductId());
        order.setStatus(Order.Status.PENDING);
        List<UUID> productIdList = order.getProductId().stream().map(Product::getId).collect(Collectors.toList());
        System.out.println(productIdList);
        List<UUID> validProductId = productRepository.findAllById(productIdList).stream().map(Product::getId).toList();
        System.out.println(validProductId);
        List<UUID> notValidIdList = productIdList.stream()
                .filter(u -> !validProductId.contains(u))
                .toList();
        Order processedOrder;
        if (notValidIdList.isEmpty()) {
            processedOrder = orderRepository.save(order);
            orderProcessingService.asyncProcess(order);
        } else {
            throw new NotFoundException("Product is not present with this ids :" + notValidIdList);
        }
        return processedOrder;
    }

    public Order getOrderStatus(UUID uuid) {
        Optional<Order> order = orderRepository.findById(uuid);
        if (order.isPresent()) {
            return order.get();
        }
        throw new NotFoundException("Order is not present with this id :" + uuid);
    }


}
