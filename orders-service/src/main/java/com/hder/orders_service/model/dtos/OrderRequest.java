package com.hder.orders_service.model.dtos;

import java.util.List;

public record OrderRequest(List<OrderItemsRequest> orderItems) {
}
