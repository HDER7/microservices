package com.hder.products_service.model.dtos;

import com.hder.products_service.model.entities.Product;

public record ProductResponse(Long id, String sku, String name, String description, Double price, Boolean status) {

    public ProductResponse(Product newProduct) {
        this(newProduct.getId(), newProduct.getSku(), newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getStatus());
    }
}
