package com.quicken.orders.order_processing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quicken.orders.order_processing.dto.OrderDTO;
import com.quicken.orders.order_processing.dto.ProductDTO;
import com.quicken.orders.order_processing.entity.Order;
import com.quicken.orders.order_processing.entity.Product;
import com.quicken.orders.order_processing.exception.NotFoundException;
import com.quicken.orders.order_processing.repository.OrderRepository;
import com.quicken.orders.order_processing.repository.ProductRepository;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderProcessingService orderProcessingService;

    @InjectMocks
    private OrderService orderService;


    @Test
    void createOrdereExceptionWhenProuctNotFound() throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        URL resourceUrl = getClass().getClassLoader().getResource("test/order.json");
        File jsonFile = new File(resourceUrl.toURI());
        Order order = mapper.readValue(jsonFile, Order.class);

        URL resourceUrlOrderRequest = getClass().getClassLoader().getResource("test/orderrequest.json");
        File jsonFilerequest = new File(resourceUrlOrderRequest.toURI());
        OrderDTO orderDTO=mapper.readValue(jsonFilerequest, OrderDTO.class);

        List<UUID> inputIds = List.of();

        when(productRepository.findAllById(any(List.class))).thenReturn(inputIds);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        assertThrows(NotFoundException.class,()->{
            orderService.createOrder(orderDTO);
        });

    }


    @Test
    void getOrderStatus() throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();

        URL resourceUrl = getClass().getClassLoader().getResource("test/order.json");
        File jsonFile = new File(resourceUrl.toURI());
        Order order = mapper.readValue(jsonFile, Order.class);
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(order));
      Order ordeResponse=  orderService.getOrderStatus(UUID.randomUUID());
        assertEquals(Order.Status.PENDING,ordeResponse.getStatus());
    }

    @Test
    void getOrderStatusNotFoundException() throws IOException, URISyntaxException {

        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(NotFoundException.class,()->{
            orderService.getOrderStatus(UUID.randomUUID());
        });

    }
}