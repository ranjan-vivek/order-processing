package com.quicken.orders.order_processing.service;

import com.quicken.orders.order_processing.entity.Order;
import com.quicken.orders.order_processing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OrderProcessingService {

    @Autowired
    private OrderRepository orderRepository;
    private final Random random = new Random();

    @Async
    public void asyncProcess(Order order) {

        try {
            Thread.sleep(3000);
            order.setStatus(Order.Status.PROCESSING);

            orderRepository.save(order);
            Thread.sleep(3000);

            boolean failed = random.nextBoolean();
            if (failed) {
                order.setStatus(Order.Status.FAILED);
            } else {
                order.setStatus(Order.Status.COMPLETED);
            }

            orderRepository.save(order);


        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }
    }


}
