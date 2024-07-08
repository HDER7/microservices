package com.hder.products_service.services;

import com.hder.products_service.model.dtos.ProductRequest;
import com.hder.products_service.model.dtos.ProductResponse;
import com.hder.products_service.model.entities.Product;
import com.hder.products_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse addProduct(ProductRequest product) {
        var newProduct = Product.builder()
                .sku(product.sku())
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .status(product.status())
                .build();
        productRepository.save(newProduct);
        log.info("Product added successfully\n {}", newProduct);
        return new ProductResponse(newProduct);
    }

    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();
        return products.stream().map(ProductResponse::new).toList();
    }

}
