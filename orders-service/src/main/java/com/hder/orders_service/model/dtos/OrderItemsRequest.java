package com.hder.orders_service.model.dtos;

public record OrderItemsRequest(Long id, String sku, Double price, Long quantity) {
}
