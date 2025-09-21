package com.quicken.orders.order_processing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quicken.orders.order_processing.entity.Order;
import com.quicken.orders.order_processing.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
class OrderProcessingServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderProcessingService orderProcessingService;


    @Test
    void testAsyncProcess_updatesStatusCorrectly() throws InterruptedException, IOException, URISyntaxException, ExecutionException {

        URL resourceUrl = getClass().getClassLoader().getResource("test/order.json");
        File jsonFile = new File(resourceUrl.toURI());
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(jsonFile, Order.class);
        CompletableFuture.runAsync(() -> orderProcessingService.asyncProcess(order)).get();

        // Verify save was called at least twice (PROCESSING → FINAL)
        verify(orderRepository, atLeast(2)).save(any(Order.class));

        // Final status should be COMPLETED or FAILED
        assertTrue(order.getStatus() == Order.Status.COMPLETED || order.getStatus() == Order.Status.FAILED);



    }

}