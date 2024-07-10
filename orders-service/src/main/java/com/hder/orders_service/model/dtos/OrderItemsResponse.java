package com.hder.orders_service.model.dtos;

import com.hder.orders_service.model.entities.OrderItems;

public record OrderItemsResponse(Long id, String sku, Double price, Long quantity ) {
    public OrderItemsResponse(OrderItems orderItems) {
        this(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
    }
}
