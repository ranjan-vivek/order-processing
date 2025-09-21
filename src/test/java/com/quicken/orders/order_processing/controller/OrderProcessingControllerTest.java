package com.quicken.orders.order_processing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quicken.orders.order_processing.dto.OrderDTO;
import com.quicken.orders.order_processing.dto.ProductDTO;
import com.quicken.orders.order_processing.entity.Order;
import com.quicken.orders.order_processing.entity.Product;
import com.quicken.orders.order_processing.exception.UUIDFormatException;
import com.quicken.orders.order_processing.service.OrderProcessingService;
import com.quicken.orders.order_processing.service.OrderService;
import com.quicken.orders.order_processing.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.quicken.orders.order_processing.entity.Product;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Set;
import java.util.UUID;

import com.quicken.orders.order_processing.entity.Order;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
class OrderProcessingControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderProcessingService orderProcessingService;
    @InjectMocks
    private OrderProcessingController orderProcessingController;

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void addProduct() throws IOException, URISyntaxException {
        URL resourceUrl = getClass().getClassLoader().getResource("test/product.json");
        File jsonFile = new File(resourceUrl.toURI());
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(jsonFile, Product.class);
        System.out.println(product);

        when(productService.addProduct(any(ProductDTO.class))).thenReturn(product);

        ProductDTO productDTO=new ProductDTO();
        productDTO.setName("vivek");
        productDTO.setPrice(2.0);

        Product productResponse=orderProcessingController.addProduct(productDTO).getBody();
        assertEquals(product.getName(),productResponse.getName());
        assertEquals(product.getPrice(),productResponse.getPrice());
        assertNotNull(productResponse.getId());
    }

    @Test
    void addProductValidArgument() throws IOException, URISyntaxException {
        ProductDTO productDTO=new ProductDTO();

        productDTO.setPrice(2.0);

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);

        assertFalse(violations.isEmpty());

        ConstraintViolation<ProductDTO> violation = violations.iterator().next();
        assertEquals("Name is required", violation.getMessage());

    }

    @Test
    void createOrder() throws URISyntaxException, IOException {
        URL resourceUrl = getClass().getClassLoader().getResource("test/order.json");
        File jsonFile = new File(resourceUrl.toURI());
        URL resourceUrlOrderRequest = getClass().getClassLoader().getResource("test/orderrequest.json");
        File jsonFilerequest = new File(resourceUrlOrderRequest.toURI());
        ObjectMapper mapper = new ObjectMapper();
        OrderDTO orderDTO=mapper.readValue(jsonFilerequest, OrderDTO.class);
        Order order = mapper.readValue(jsonFile, Order.class);
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(order);

        Order orderResponse = (Order) orderProcessingController.createOrder(orderDTO).getBody();
        assertNotNull(orderResponse.getId());


    }

    @Test
    void getOrderStatus() throws IOException, URISyntaxException {
        String id="42d3aadc-c3d7-4cef-a0f1-5ad08d075735";
        URL resourceUrl = getClass().getClassLoader().getResource("test/order.json");
        File jsonFile = new File(resourceUrl.toURI());
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(jsonFile, Order.class);
        when(orderService.getOrderStatus(any(UUID.class))).thenReturn(order);

        Order orderResponse =orderProcessingController.getOrderStatus(id).getBody();
        assertEquals(order.getId(),orderResponse.getId());
        assertEquals(order.getProductId().get(0),orderResponse.getProductId().get(0));
    }

    @Test
    void getOrderStatusUUIdFormatException() throws IOException, URISyntaxException {
        String id="42d3aadc-c3d7-4cef-a0f1-5ad08d0757351";
        URL resourceUrl = getClass().getClassLoader().getResource("test/order.json");
        File jsonFile = new File(resourceUrl.toURI());
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(jsonFile, Order.class);
        when(orderService.getOrderStatus(any(UUID.class))).thenReturn(order);
        assertThrows(UUIDFormatException.class,()->
        { orderProcessingController.getOrderStatus(id); });

    }
}