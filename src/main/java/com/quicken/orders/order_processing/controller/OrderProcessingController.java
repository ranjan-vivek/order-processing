package com.quicken.orders.order_processing.controller;

import com.quicken.orders.order_processing.dto.OrderDTO;
import com.quicken.orders.order_processing.dto.ProductDTO;
import com.quicken.orders.order_processing.entity.Order;
import com.quicken.orders.order_processing.entity.Product;
import com.quicken.orders.order_processing.exception.UUIDFormatException;
import com.quicken.orders.order_processing.service.OrderProcessingService;
import com.quicken.orders.order_processing.service.OrderService;
import com.quicken.orders.order_processing.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Order Controller", description = "APIs for order processing")
public class OrderProcessingController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProcessingService orderProcessingService;

    @PostMapping("/products")
    @Operation(
            summary = "add product ",
            description = "Add product with given details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")

            }
    )
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


    @PostMapping("/orders")
    @Operation(
            summary = "add order ",
            description = "Add order with given details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully"),
                    @ApiResponse(responseCode = "404", description = "If product not found")

            }
    )
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/orders/{id}")
    @Operation(
            summary = "get order",
            description = "Add order with given details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product created successfully"),
                    @ApiResponse(responseCode = "400", description = "UUId format exception")

            }
    )
    public ResponseEntity<Order> getOrderStatus(@PathVariable String id) {

        try {
            if (id.length() == 32) {
                    id = id.replaceAll(
                        "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                        "$1-$2-$3-$4-$5"
                );
            }

            UUID uuid = UUID.fromString(id);
            Order order = orderService.getOrderStatus(uuid);
            return ResponseEntity.status(HttpStatus.OK).body(order);

        } catch (IllegalArgumentException e) {
            throw new UUIDFormatException("Invalid UUID format");

        }

    }
}
