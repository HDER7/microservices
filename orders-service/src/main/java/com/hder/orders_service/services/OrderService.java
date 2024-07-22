package com.hder.orders_service.services;

import com.hder.orders_service.events.OrderEvent;
import com.hder.orders_service.model.dtos.BaseResponse;
import com.hder.orders_service.model.dtos.OrderItemsRequest;
import com.hder.orders_service.model.dtos.OrderRequest;
import com.hder.orders_service.model.dtos.OrderResponse;
import com.hder.orders_service.model.entities.Order;
import com.hder.orders_service.model.entities.OrderItems;
import com.hder.orders_service.model.enums.OrderStatus;
import com.hder.orders_service.repositories.OrderRepository;
import com.hder.orders_service.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        //verificar inventario
        BaseResponse result = webClientBuilder.build()
                .post()
                .uri("lb://inventory-service/api/inventory/in-stock")
                .bodyValue(orderRequest.orderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if (result != null && !result.hasError()){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.orderItems().stream().map(orderItemsRequest -> mapOrderItemRequestToOrderItem(orderItemsRequest, order)).toList());
            this.orderRepository.save(order);
            //TODO send message to order topic
            this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(new OrderEvent(order.getOrderNumber(), order.getOrderItems().size(), OrderStatus.PLACED)));

            return new OrderResponse(order);
        }
        throw new IllegalArgumentException("Some of the products are not available");
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream().map(OrderResponse::new).toList();
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemsRequest orderItemsRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemsRequest.id())
                .sku(orderItemsRequest.sku())
                .price(orderItemsRequest.price())
                .quantity(orderItemsRequest.quantity())
                .order(order).build();
    }
}
