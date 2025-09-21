package com.quicken.orders.order_processing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quicken.orders.order_processing.dto.ProductDTO;
import com.quicken.orders.order_processing.entity.Product;
import com.quicken.orders.order_processing.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void addProduct() throws IOException, URISyntaxException {
        URL resourceUrl = getClass().getClassLoader().getResource("test/product.json");
        File jsonFile = new File(resourceUrl.toURI());
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(jsonFile, Product.class);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDTO productDTO=new ProductDTO();
        productDTO.setName("vivek");
        productDTO.setPrice(2.0);
        Product productResponse=productService.addProduct(productDTO);
        assertEquals(product.getName(),productResponse.getName());
        assertEquals(product.getPrice(),productResponse.getPrice());
        System.out.println(productResponse);
        assertNotNull(productResponse.getId());
    }
}