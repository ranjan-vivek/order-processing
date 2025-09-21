package com.quicken.orders.order_processing.service;

import com.quicken.orders.order_processing.dto.ProductDTO;
import com.quicken.orders.order_processing.entity.Product;
import com.quicken.orders.order_processing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return productRepository.save(product);
    }

}
