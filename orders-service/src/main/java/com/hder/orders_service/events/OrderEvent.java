package com.hder.orders_service.events;

import com.hder.orders_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
