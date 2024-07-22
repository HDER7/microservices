package com.hder.notification_service.events;

import com.hder.notification_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
