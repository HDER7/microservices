package com.hder.orders_service.model.dtos;

import com.hder.orders_service.model.entities.Order;

import java.util.List;

public record OrderResponse(Long id, String orderNumber, List<OrderItemsResponse> orderItems) {

    public OrderResponse(Order order) {
        this(order.getId(), order.getOrderNumber(),order.getOrderItems().stream().map(OrderItemsResponse::new).toList());
    }
}
